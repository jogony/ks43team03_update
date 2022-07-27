package ks43team03.dto;

import ks43team03.dto.type.PayType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Order {

	private String orderCd;
	private String userId;
//	private String userName;
	private String facilityGoodsCd; //예약신청코드 
	private String goodsCtgCd; //카테고리코드 이것도 
	private int orderPrice;//가격
	private int userPoint; //회원이보유한 포인트 point
	private int usedPoint;
	private int orderPayPrice; // 실결제 금액
	private String orderRegDate; //  주문일자
	//private OrderState orderPayState; // 주문/결제상태 enum테스트
	private String orderPayState;
	private PayType payType;
	private String orderDelYN; // 주문삭제 상태 
	private String orderDelDate; // 주문삭제일시 
	
	
	private Pay pay;
	
	private boolean isCancelDay;
	
	// 토스 주문ㅇㅏ이디
	private String orderUUID;
	// 상품 이름 흠.
	private String goodsName;
	
	private String payName;
	
	private String userName;
	
	
	private FacilityGoods facilityGoods;
	private Facility facility;
	//User객체를 + 테스트하기위해 만든 메소드
	
	
	
	public Order() {}
	
	@Builder
	public Order(String orderCd,
			String userId, 
			String facilityGoodsCd, 
			String goodsCtgCd, 
			int orderPrice, 
			int userPoint, 
			int usedPoint,
			int orderPayPrice, 
			String orderPayState,
			String goodsName,
			PayType payType,
			String orderUUID) {
		
		this.orderCd = orderCd;
		this.userId = userId;
		this.facilityGoodsCd = facilityGoodsCd;
		this.goodsCtgCd = goodsCtgCd;
		this.orderPrice = orderPrice;
		this.userPoint = userPoint;
		this.usedPoint = usedPoint;
		this.orderPayPrice = orderPayPrice;
		this.orderPayState = orderPayState;
		this.goodsName = goodsName;
		this.payType = payType;
		this.orderUUID = orderUUID;
	}
	
	
	
	@Getter
	@Setter
	public static class Request{
		private String userId;
		private String facilityGoodsCd; 
		private String goodsCtgCd;
		private String goodsName;
		private int orderPrice;
		private int userPoint;
		private int usedPoint;
		private int orderPayPrice;
		private PayType payType;
		
		
		


		
	}


	@Getter
	public static class Response{
		private String orderId;
		private String customerName;
		private String goodsPrice;
		private String totalPrice;
		private String payTypeName;
		
	}

}
