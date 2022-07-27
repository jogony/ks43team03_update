package ks43team03.dto;

import ks43team03.dto.type.PayStatus;
import lombok.Data;

@Data
public class PaymentResDto {

	
	 private String mId; //"mId": "tosspayments",
	 private String version; // "version": "1.4",
	 private String paymentKey;// "paymentKey": "q8TAdB4AXbl_8Z8nAHcMB",
	 private String orderId; //"orderId": "_en8MVAhWzLeE1SXnC9uh",
	 private String orderName; //"orderName": "토스 티셔츠 외 2건",
	 private String currency; //"currency": "KRW",
	 private String method; //"method": "카드",
	 private PayStatus status; // "status": "DONE",
	 private String requestedAt; // "requestedAt": "2021-01-01T10:01:30+09:00",
	 private String approvedAt; // "approvedAt": "2021-01-01T10:05:40+09:00",
	 private String useEscrow; // "useEscrow": false,
	 private String cultureExpense; // "cultureExpense": false,
	 private String totalAmount; //"totalAmount": 15000,
	 private String balanceAmount; // "balanceAmount": 15000,
	 private String suppliedAmount; // "suppliedAmount": 13636,
	 private String vat; // "vat": 1364,
	 private String taxFreeAmount; // "taxFreeAmount": 0
	 private PaymentCardResDto card; // 카드
	 private String type;
	 private PaymentVirtualResDto virtualAccount; // 가상계좌
	 
	 // private EasyPayType easyPay; // 안받아지네 
	 
	 private String userId;
}
