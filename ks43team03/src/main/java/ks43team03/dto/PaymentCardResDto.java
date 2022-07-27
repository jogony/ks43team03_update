package ks43team03.dto;

import lombok.Data;

@Data
public class PaymentCardResDto {

	private String company; //"company": "현대",
	private String number; //"number": "433012******1234",
	private String installmentPlanMonths; //"installmentPlanMonths": 0,
	private String isInterestFree; //"isInterestFree": false,
	private String interestPayer; //"interestPayer": null,
	private String approveNo; //"approveNo": "00000000",
	private String useCardPoint; //"useCardPoint": false,
	private String cardType; //"cardType": "신용",
	private String ownerType; //"ownerType": "개인",
	private String acquireStatus; //"acquireStatus": "READY",
	private String receiptUrl; //"receiptUrl": "https://merchants.tosspayments.com/web/serve/merchant/test_ck_jkYG57Eba3GoelzLmLQ3pWDOxmA1/receipt/rlOqkp81hUb-VW5L6995D"
  
}
