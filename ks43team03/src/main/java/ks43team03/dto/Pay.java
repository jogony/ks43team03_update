package ks43team03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pay {

	
	private String payCd;
	private String orderCd;
	private String userId;
	private int payUsedPoint; // 사용포인트
	private String payMethod; // 결제수단정보
	private String paymentKey; // 결제 정보 환불 조회시 필요
	private String orderUUID;  // 주문 UUID
	private int payPrice;  // 상품가격
	private String payTotalPrice; // 총결제금액
	private String payRegDate; // 결제일시
	private String payState; // 결제 상태 결제성공 결제 취소  결제진행중 
	private String payDelYN; // 결제 삭제상태
	private String payDelDate; // 결제 삭제일시 
	
	//card 결제 정보
	
	private String payCompany; //  결제 카드회사
	private String payCardNumber; //결제 카드번호
	private String payCardType; // 결제 카드 종류
	
	// 가상계좌 결제 정보 
	private String payBank;	// 가상계좌 은행
	private String payUserName; // 예금주
	private String accountNumber; // 가상계좌 번호
	private String payDueDate; //입금 기한 
	
	public Pay() {}
	
	@Builder
	public Pay(String orderCd, String userId, int payUsedPoint, String payMethod, String paymentKey, String orderUUID,
			int payPrice, String payTotalPrice, String payState) {
		this.orderCd = orderCd;
		this.userId = userId;
		this.payUsedPoint = payUsedPoint;
		this.payMethod = payMethod;
		this.paymentKey = paymentKey;
		this.orderUUID = orderUUID;
		this.payPrice = payPrice;
		this.payTotalPrice = payTotalPrice;
		this.payState = payState;
	}
	
}
