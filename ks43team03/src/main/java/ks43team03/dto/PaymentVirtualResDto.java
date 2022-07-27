package ks43team03.dto;

import lombok.Data;

@Data
public class PaymentVirtualResDto {
	
	private String accountNumber; //"accountNumber": "X6505636518308",
	private String accountType; //   "accountType": "일반",
	private String bank; //   "bank": "우리",
	private String customerName; //   "customerName": "박토스",
	private String dueDate; //   "dueDate": "2022-01-15T21:05:09+09:00",
	private String expired; //   "expired": false,
	private String settlementStatus; //   "settlementStatus": "INCOMPLETED",
	private String refundStatus; //   "refundStatus": "NONE"
}
