package ks43team03.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.common.TossApi;
import ks43team03.dto.Order;
import ks43team03.dto.Pay;
import ks43team03.dto.Payload;
import ks43team03.dto.PaymentCardResDto;
import ks43team03.dto.PaymentResDto;
import ks43team03.dto.PaymentVirtualResDto;
import ks43team03.dto.type.OrderState;
import ks43team03.dto.type.PayStatus;
import ks43team03.dto.type.PayType;
import ks43team03.exception.CustomException;

import static ks43team03.exception.ErrorMessage.*;
import ks43team03.mapper.OrderMapper;
import ks43team03.mapper.PayMapper;

@Service
@Transactional
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	private final OrderMapper orderMapper;
	private final PayMapper payMapper;
	private final TossApi tossApi;

	public PaymentService(OrderMapper orderMapper, PayMapper payMapper, TossApi tossApi) {
		this.orderMapper = orderMapper;
		this.payMapper = payMapper;
		this.tossApi = tossApi;
	}

	/*
	 * 결제 후 토스 에서 결제 승인데이터를 받고 결제 테이블에 등록하는 메소드
	 */
	public PaymentResDto confirmPayment(String paymentKey, String orderId, Long amount) {

		// 주문 정보 확인 한다.
		Order order = orderMapper.getOrderByOrderUUID(orderId).orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));
		// toss 결제 최종 확인을한다.
		PaymentResDto paymentResDto;
		try {
			paymentResDto = tossApi.confirmPayment(paymentKey, orderId, amount);
		} catch (Exception e) {
			log.error("결제의 승인을 하지 못하였습니다.");
			throw new CustomException(NOT_FOUND_PAYMENT);
		}

		log.info("토스 응답데이터 :  {}", paymentResDto);

		paymentResDto.setUserId(order.getUserId());

		// 카드또는 가상계좌 종류 이므로 if문으로 비교 후 카드로 결제했다면 주문완료 와 결제 완료 표시

		// 계좌 입금으로 처리할시 결제상태와 주문상태 주문중 상태
		if (PayStatus.DONE == paymentResDto.getStatus()) {
			order.setOrderPayState(OrderState.COMPLETE.getCode());
		} else if (PayStatus.CANCELED == paymentResDto.getStatus()) {
			order.setOrderPayState(OrderState.CANCEL.getCode());
		}

		Pay pay = Pay.builder()
				.orderCd(order.getOrderCd())
				.payMethod(paymentResDto.getMethod())
				.payPrice(order.getOrderPrice())
				.orderUUID(paymentResDto.getOrderId())
				.payTotalPrice(paymentResDto.getTotalAmount())
				.userId(order.getUserId())
				.payUsedPoint(order.getUsedPoint())
				.paymentKey(paymentResDto.getPaymentKey())
				.payState(paymentResDto.getStatus().getMessage())
				.build();

		// 주문상태 변경
		orderMapper.modifyOrder(order);

		// 결제정보 등록
		payMapper.addPay(pay);

		// 등록에 실패한다면 예외발생
		if (pay.getPayCd() == null) {
			throw new CustomException(DATABASE_ERROR);
		}

		// 최종적으로 if문 통과시 가상계좌 or 카드 결과 셋팅 ex 카드는 카드번호 카드회사 가상계좌는 계좌은행 계좌번호 예금주
		addPaySetting(pay, paymentResDto, order);

		// 통과후 토스 응답데이터 리턴
		return paymentResDto;

	}

	private void addPaySetting(Pay pay, PaymentResDto paymentResDto, Order order) {
		String payMethod = paymentResDto.getMethod();
		int result = 0;
		if (PayType.CARD.equals(order.getPayType())) {
			log.info("===========카드로 결제===========");
			// 간편결제시 삼성폐이 = card
			if (paymentResDto.getCard() != null) {
				// == 카드 정보가 있다면 ==//
				PaymentCardResDto res = paymentResDto.getCard();
				pay.setPayCardType(res.getCardType());
				pay.setPayCompany(res.getCompany());
				pay.setPayCardNumber(res.getNumber());
				result = payMapper.addPayCardInfo(pay);
			} else if (paymentResDto.getVirtualAccount() != null) {
				// == 가상계좌 정보가 있다면 ==//
				PaymentVirtualResDto res = paymentResDto.getVirtualAccount();
				pay.setPayBank(res.getBank());
				pay.setPayUserName(res.getCustomerName());
				pay.setAccountNumber(res.getAccountNumber());
				pay.setPayDueDate(res.getDueDate());
				result = payMapper.addPayVirtualAccount(pay);
			}
		} else if (PayType.VIRTUAL_ACCOUNT.equals(order.getPayType())) {
			log.info("===========가상계좌로 결제===========");
			PaymentVirtualResDto res = paymentResDto.getVirtualAccount();
			pay.setPayBank(res.getBank());
			pay.setPayUserName(res.getCustomerName());
			pay.setAccountNumber(res.getAccountNumber());
			pay.setPayDueDate(res.getDueDate());
			result = payMapper.addPayVirtualAccount(pay);
		}
		
		if(result == 0) {
			
		}
	}

	/*
	 * 
	 */
	public void handleVirtualAccountCallback(Payload payload) {

		String status = payload.getStatus();
		String orderId = payload.getOrderId();
		String secret = payload.getSecret();

		// 주문정보 조회
		Order order = orderMapper.getOrderByOrderUUID(orderId)
				.orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));
		Pay pay = payMapper.getPayByOrderCd(order.getOrderCd())
				.orElseThrow(()-> new CustomException(NOT_FOUND_PAYMENT));
		if ("DONE".equals(status)) {
			order.setOrderPayState(OrderState.COMPLETE.getCode());
			pay.setPayState(PayStatus.DONE.getMessage());
			int r = orderMapper.modifyOrder(order);
			if(r == 0) {
				throw new CustomException(ORDER_MODIFY_ERROR);
			}
		} else if ("CANCELED".equals(status)) {
			// 입금을 취소시
			order.setOrderPayState(OrderState.CANCEL.getCode());
			pay.setPayState(PayStatus.CANCELED.getMessage());
		}
	}

	/*
	 * 결제 실패 메소드
	 */
	public void failPayment(String orderId) {

		// 주문정보를 조회후 주문 취소를 해야한다 주문 등록 -> 결제 하기 때문

		// order정보 조회
		Order order = orderMapper.getOrderByOrderUUID(orderId)
				.orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));
		try {
			order.setOrderPayState(OrderState.FAIL.getCode());
			order.setOrderDelYN("Y");
			order.setOrderDelDate("y");
			// 삭제 또는 업데이트 처리 <<
			int result = orderMapper.modifyOrder(order);
			if (result == 0) {
				throw new CustomException(ORDER_DELETE_ERROR);
			}
		} catch (Exception e) {
			throw new CustomException(DATABASE_ERROR);
		}
		// order 상태 yn y세팅하거나 삭제

	}

	/*
	 * 결제 후 데이터 조회 메소드
	 */
	public PaymentResDto findTossPaymentsbyOrderId(String orderId) {
		try {
			PaymentResDto paymentResDto = tossApi.findTossPaymentsbyOrderId(orderId);
			log.info("조회 요청 데이터 : {}", paymentResDto);
			return paymentResDto;
		} catch (Exception e) {
			throw new CustomException(NOT_FOUND_PAYMENT);
		}
	}

	public List<Pay> getAllPayment() {

		return null;
	}

	// 회원이 조회한 결제정보
	public List<Pay> getAllPaymentByUserId(String userId) {

		return null;
	}

}
