package ks43team03.service;



import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.Order;
import ks43team03.dto.ResponseGoods;
import ks43team03.dto.type.PayType;
import ks43team03.exception.CustomException;
import ks43team03.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;



//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
@Transactional
public class OrderServiceTest {

	@Autowired
	OrderService orderService;
	
//	@Test
//	@DisplayName("정상흐름 주문에 성공을한다.")
//	void createOrder() {
//		// given
//		Order.Request orderReq = new Order.Request();
//
//		ResponseGoods goods = responseGoods();
//		orderReq.setFacilityGoodsCd(goods.getFacilityGoods().getFacilityGoodsCd());
//		orderReq.setPayType(PayType.CARD);
//		orderReq.setUserId("id001");
//		orderReq.setOrderPayPrice(400000);
//		orderReq.setOrderPrice(goods.getPrice());
//		orderReq.setUsedPoint(1000);
//		orderReq.setGoodsName("고급 강좌");
//		
//		//when
//		Order order = orderService.addOrder(orderReq, goods);
//		
//		
//		//then
//		assertThat(order.getFacilityGoodsCd()).isEqualTo(orderReq.getFacilityGoodsCd());
//		
//
//	}
	
// 	@Test
// 	@DisplayName("한명의 여러개의 주문리스트")
// 	void findOne() {
// //	 	List<Order>list = orderService.getOrdersByUser("id002");
// //	 	
// //	 	log.info("리스트에 담긴 회원의 주문내역 데이터 : {}", list);
// 	}
	
	/**
	 * 
	 */
	@Test
	@DisplayName("주문 상품가격이 상품가격을 초과하는 경우 예외가 발생한다.")
	void priceExceed(){
		
		Order.Request orderReq = new Order.Request();

		ResponseGoods goods = responseGoods();
		orderReq.setFacilityGoodsCd(goods.getFacilityGoods().getFacilityCd());
		orderReq.setPayType(PayType.CARD);
		orderReq.setUserId("id001");
		orderReq.setOrderPayPrice(600000);
		orderReq.setOrderPrice(goods.getPrice());
		orderReq.setUsedPoint(1000);
		orderReq.setGoodsName("고급 강좌");



		Throwable exception = Assertions.assertThrows(CustomException.class, ()->{
			orderService.addOrder(orderReq, goods);
		});
		
		log.debug("error Massage : {}", exception.getMessage());
		assertEquals(ErrorMessage.ORDER_ERROR_ORDER_PRICE.getMessage(), exception.getMessage());
	}
	


	// @Test
	// @DisplayName("존재하지 않은 결제타입으로 주문을 할시 예외가 발생한다.")
	// void nonPayType(){
	// 	Order.Request orderReq = new Order.Request();
	// 	ResponseGoods goods = responseGoods();
	// 	orderReq.setFacilityGoodsCd(goods.getFacilityGoods().getFacilityCd());
	// 	//orderReq.setPayType(PayType.CARD);
	// 	orderReq.setUserId("id001");
	// 	orderReq.setOrderPayPrice(600000);
	// 	orderReq.setOrderPrice(goods.getPrice());
	// 	orderReq.setUsedPoint(1000);
	// 	orderReq.setGoodsName("고급 강좌");

	// 	Throwable exception = Assertions.assertThrows(CustomException.class, ()->{
	// 		orderService.addOrder(orderReq, goods);
	// 	});
		
	// 	log.debug("error Massage : {}", exception.getMessage());
	// 	assertEquals(ErrorMessage.NOT_EXITS_PAYMENT_TYPE_ERROR.getMessage(), exception.getMessage());
	// }

//== null ==//	
	@Test
	@DisplayName("세션이 풀리고 로그인되지 않은 아이디로 주문조회를 하면 예외가 발생한다.")
	void sessionOut(){
			
		Order.Request orderReq = new Order.Request();

		ResponseGoods goods = responseGoods();
		orderReq.setFacilityGoodsCd(goods.getFacilityGoods().getFacilityCd());
		orderReq.setPayType(PayType.CARD);
		orderReq.setUserId(null);
		orderReq.setOrderPayPrice(400000);
		orderReq.setOrderPrice(goods.getPrice());
		orderReq.setUsedPoint(1000);
		orderReq.setGoodsName("고급 강좌");


		Throwable exception = Assertions.assertThrows(CustomException.class, ()->{
			orderService.addOrder(orderReq, goods);
		});
		
		log.debug("error Massage : {}", exception.getMessage());
		assertEquals(ErrorMessage.IS_EMPTY_USER.getMessage(), exception.getMessage());
	}
	
	
	@Test
	@DisplayName("금액이 0원 미만일 경우 예외가 발생한다. ")
	void enterNegativeAmountOfMoney(){
		Order.Request orderReq = new Order.Request();

		ResponseGoods goods = responseGoods();
		orderReq.setFacilityGoodsCd(goods.getFacilityGoods().getFacilityCd());
		orderReq.setPayType(PayType.CARD);
		orderReq.setUserId(null);
		orderReq.setOrderPayPrice(-10);
		orderReq.setOrderPrice(goods.getPrice());
		orderReq.setUsedPoint(1000);
		orderReq.setGoodsName("고급 강좌");

		Throwable exception = Assertions.assertThrows(CustomException.class, ()->{
			orderService.addOrder(orderReq, goods);
		});
		
		log.debug("금액이 마이너스를 입력시 exception", exception.getMessage());

		log.debug("error Massage : {}", exception.getMessage());
		assertEquals(ErrorMessage.ORDER_ERROR_ORDER_PRICE.getMessage(), exception.getMessage());

	}

	
	@Test
	@DisplayName("존재하지 않은 상품으로 구매를 하였을 경우")
	void notFoundGoods(){

	}


	private ResponseGoods responseGoods(){
		
		FacilityGoods goods = facilityGoods();
		
		return ResponseGoods.builder()
		.facilityGoods(goods)
		.price(500000)
		.build();
	}

	private FacilityGoods facilityGoods(){
		FacilityGoods facilityGoods = new FacilityGoods();
		facilityGoods.setFacilityGoodsCd("ss_35011740_01_lesson_01");
		facilityGoods.setGoodsCtgCd("lesson");
		return facilityGoods;
	}
	
}
