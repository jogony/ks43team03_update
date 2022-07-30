package ks43team03.service;


import static ks43team03.exception.ErrorMessage.DATABASE_ERROR;
import static ks43team03.exception.ErrorMessage.IS_EMPTY_USER;
import static ks43team03.exception.ErrorMessage.NOT_EXITS_PAYMENT_TYPE_ERROR;
import static ks43team03.exception.ErrorMessage.NOT_FOUND_ORDER;
import static ks43team03.exception.ErrorMessage.ORDER_DELETE_ERROR;
import static ks43team03.exception.ErrorMessage.ORDER_ERROR_ORDER_PRICE;
import static ks43team03.exception.ErrorMessage.USER_ERROR_USER_NOT_FOUND;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Lesson;
import ks43team03.dto.Order;
import ks43team03.dto.OrderSearchDto;
import ks43team03.dto.PageDto;
import ks43team03.dto.Pass;
import ks43team03.dto.ResponseGoods;
import ks43team03.dto.User;
import ks43team03.dto.type.GoodsType;
import ks43team03.dto.type.OrderState;
import ks43team03.exception.CustomException;
import ks43team03.mapper.FacilityGoodsMapper;
import ks43team03.mapper.OrderMapper;
import ks43team03.mapper.PassMapper;
import ks43team03.mapper.UserMapper;

@Service
@Transactional
public class OrderService {

	private final OrderMapper orderMapper;
	private final UserMapper userMapper;
	private final FacilityGoodsMapper facilityGoodsMapper;
	private final PassMapper passMapper;
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	
	public OrderService(OrderMapper orderMapper,UserMapper userMapper,
						FacilityGoodsMapper facilityGoodsMapper,PassMapper passMapper) {
		this.orderMapper = orderMapper;
		this.userMapper = userMapper;
		this.facilityGoodsMapper = facilityGoodsMapper;
		this.passMapper = passMapper;
	}


	
	

	//주문 하기
	// toss 결제전에 주문내역을 넣는다.
	public Order addOrder(Order.Request req, ResponseGoods goods) {
		
		log.info("요청 데이터의 PayType : {}", req.getPayType());
		
		String paytype = req.getPayType().getName();
		String userId = req.getUserId();
		int orderPayPrice = req.getOrderPayPrice();
		String goodsCategory = goods.getFacilityGoods().getGoodsCtgCd();
		
		
		if(!paytype.equals("카드") && !paytype.equals("가상계좌")) {
			throw new CustomException(NOT_EXITS_PAYMENT_TYPE_ERROR);
		}
		
		
		
		if("lesson".equals(goodsCategory) || "pass".equals(goodsCategory)) {
			if(orderPayPrice < 0 || orderPayPrice > goods.getPrice()) {
				throw new CustomException(ORDER_ERROR_ORDER_PRICE);
			}
		}
		
		if(Strings.isEmpty(userId)) {
			throw new CustomException(IS_EMPTY_USER);
		}
		// 예외처리
		User user;
			
		try {
			user = userMapper.getUserInfoById(userId);
			
			if(user == null) {
				throw new CustomException(USER_ERROR_USER_NOT_FOUND);
			}else {
				log.info("userName : {} , " , user.getUserName());
				
				//포인트 테이블 포인트 차감 하고 저장한다.
				// 미구현 ..
				//
			}
		}catch(Exception e){
			throw new CustomException(DATABASE_ERROR);
		}
		
		//String code = commonMapper.createNewCode(COLUMN_NAME, TABLE_NAME);
		if(!categoryGoodsSave(goodsCategory, goods)) {
			throw new CustomException("상품의 정보 수정에 실패하였습니다.");
		}
		Order order = createOrder(req, goods );
		orderMapper.addOrder(order);
		order.setPayName(paytype);
		order.setUserName(user.getUserName());
		return order;
	}

	
	private boolean categoryGoodsSave(String categry, ResponseGoods goods) {
		if("lesson".equals(categry)) {
			Lesson lesson = goods.getFacilityGoods().getLesson();
			String member =lesson.getLessonMember(); 
			int number = 0;
			if(member == null) {
				number = 1;
			}else {
				number = Integer.parseInt(member) + 1;
			}
			
			String totalMember = lesson.getLessonTotalMember();
			
			if(Integer.parseInt(totalMember) == number - 1) {
				throw new CustomException("레슨 정원이 초과하였습니다.");
			}
			
			lesson.setLessonMember(Integer.toString(number));
			int result = facilityGoodsMapper.modifyLesson(lesson);
			return result == 1;
		}
		
		
		if("pass".equals(categry)) {
			Pass pass = goods.getFacilityGoods().getPass();
			int unit = pass.getPassUnit();
			if(unit == 0) {
				throw new CustomException("이용권 정원이 마감되었습니다.");
			}
			
			pass.setPassUnit(unit - 1);
			if(unit - 1 == 0) {
				pass.setPassState("N");
			}
			
			return passMapper.modifyPass(pass) == 1;
		}
		
		return true;
	}
	
	/**
	 * request데이터 order로 변환 메소드 
	 */
	private Order createOrder(Order.Request req, ResponseGoods goods) {
		return Order.builder()
				.facilityGoodsCd(goods.getFacilityGoods().getFacilityGoodsCd())
				.goodsCtgCd(goods.getFacilityGoods().getGoodsCtgCd())
				.orderPayPrice(req.getOrderPayPrice())
				.orderUUID((UUID.randomUUID().toString()+ LocalDate.now()).replaceAll("-", ""))
				.orderPrice(req.getOrderPrice())
				.userId(req.getUserId())
				.usedPoint(req.getUsedPoint())
				.goodsName(req.getGoodsName())
				.orderPayState(OrderState.ORDER.getCode())
				.payType(req.getPayType())
				.build();
	}
	
	
	/**
	 * 
	 * @return
	 */
	
	//== 주문 조회 ==//
	public Order getOrderByCode(String orderCd) {
		Order order = orderMapper.getOrderByCode(orderCd).orElseThrow(()-> new CustomException(NOT_FOUND_ORDER));
		return order;
	}
	
	//== 주문 상세조회  ==//
	public Order getOrderDetailByOrderCd(String orderCd) {
		Order order = orderMapper.getOrderDetailByOrderCd(orderCd)
				.orElseThrow(()-> new CustomException(NOT_FOUND_ORDER));
		
		dayCheck(order);
		
		return order;
	}
	
	private void dayCheck(Order order) {
		
		LocalDate orderDate = LocalDate.parse(order.getOrderRegDate());
		LocalDate now = LocalDate.now();
		long day = orderDate.until(now,ChronoUnit.DAYS);
		if(day > 7L) {
			order.setCancelDay(true);
		}else {
			order.setCancelDay(false);
		}
	}
	
	
	//== 회원이 주문한 내역 ==//
	/**
	 *  페이징 처리 개선이 필요하다.
	 */
	@Transactional(readOnly = true)
	public List<Order> getOrdersByUser(String userId, String dateMonth,String searchValue){
		List<Order> orderList = orderMapper.getOrdersByUser(userId,dateMonth,searchValue);
		return orderList ;
	}
	
	
	public Order getOrderDetail(String orderCd) {

		Order order = getOrderByCode(orderCd);
		String goodsCtgCd = order.getGoodsCtgCd();
		switch (goodsCtgCd) {
		case "lesson":
			return orderMapper.getOrderDetailWithLesson(orderCd);
		case "stadium":
			return orderMapper.getOrderDetailWithStadium(orderCd);
		case "pass":
			return orderMapper.getOrderDetailWithPass(orderCd);
		}
		throw new CustomException(NOT_FOUND_ORDER);
	}
	
	/**
	 * orderUUID로 주문 완전 삭제 메소드 
	 */
	public void removeOrderByOrderUUID(String orderUUID){
		try {
			log.info("uuid 주문 정보 삭제 log");
			Order order = orderMapper.getOrderByOrderUUID(orderUUID)
					.orElseThrow(() -> new CustomException(NOT_FOUND_ORDER));
			
			int result = orderMapper.removeOrderByOrderUUID(orderUUID);
			if(result == 0) {
				throw new CustomException(ORDER_DELETE_ERROR);
			}
			
			if(!modifyOrderGoods(order.getFacilityGoodsCd(),order.getGoodsCtgCd())) {
				throw new CustomException("주문하신 상품삭제가 실패하였습니다.");
			}
		}catch (Exception e) {
			throw new CustomException(DATABASE_ERROR);
		}
	}
	
	private boolean modifyOrderGoods(String goodsCode,String goodsCategory) {
		switch (goodsCategory) {
		case "lesson":
			Lesson lesson = facilityGoodsMapper.getFacilityGoodsLesson(goodsCode).getLesson();
			int number = Integer.parseInt(lesson.getLessonMember()) - 1;
			lesson.setLessonMember(Integer.toString(number));
		    return facilityGoodsMapper.modifyLesson(lesson) == 1;
		case "stadium":
			return true;
		case "pass" : 
			Pass pass = facilityGoodsMapper.getFacilityGoodsPass(goodsCode).getPass();
			pass.setPassUnit(pass.getPassUnit() + 1);
			pass.setPassState("Y");
			return passMapper.modifyPass(pass) == 1;
		}
		throw new CustomException("존재하지 않은 카테고리가 입력되었습니다.");
	}
	
	
	public List<Order> orderInfomationByCategory(GoodsType category, String userId) {
		log.info("category data : {}", category.getCode());
		List<Order> orderList = orderMapper.orderInfomationByCategory(category.getCode(), userId);
		return orderList;
	}
	
	
	/**
	 * 관리자 주문리스트 
	 * @return
	 */
	@Transactional(readOnly = true)
	public PageDto<Order> getOrderAll(int currentPage){
		double rowCount = orderMapper.getOrderCount();
		PageDto<Order> page = new PageDto<Order>(rowCount, currentPage, 10);
		List<Order> orderList =  orderMapper.getOrderAll(page);
		page.setList(orderList);
		return page;
	}
	
	public PageDto<Order> getSearchOrderList(OrderSearchDto orderSearchDto,int currentPage){
 		double rowCount =  orderMapper.getSearchOrderCount(orderSearchDto);

		PageDto<Order> page = new PageDto<>(rowCount, currentPage, 10);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("search", orderSearchDto);
		param.put("page", page);
		
		List<Order> orderList = orderMapper.getSearchOrderList(param);
		page.setList(orderList);
		return page;
	}
	
	
	/*
	 * 
	 * 시실의 주문리스트
	 * 중복되는 코드 개선 해야 함.
	 */
	public PageDto<Order> getOrderInfoForFacility(String facilityCd,int currentPage){
		double rowCount = orderMapper.getOrderByFacilityCount(facilityCd);
		PageDto<Order> page = new PageDto<Order>(rowCount, currentPage, 10,facilityCd);
		List<Order> orderList = orderMapper.getOrderInfoForFacility(page);
		log.info("db 조회 데이터 : {}", orderList);
		
		page.setList(orderList);
		return page;
	}
	
}
