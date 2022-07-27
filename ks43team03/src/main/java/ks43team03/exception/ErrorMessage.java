package ks43team03.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
	
	DATABASE_ERROR("데이터베이스 오류가 발생하였습니다."),
	NOT_FOUND_ORDER("해당 주문내역이 없습니다."),
	NOT_FOUND_GOODS("해당 상품이 없습니다."),
	NOT_FOUND_FACILITY("존재하지 않은 시설정보입니다."),
	GOODS_ERROR_NOT_FOUND_PASS("존재하지 않은 이용권 입니다"),
	GOODS_ERROR_NOT_FOUND_LESSON("존재하지 않은 레슨정보 입니다"),
	GOODS_ERROR_NOT_FOUND_STADIUM("존재하지 않은 시설대관정보입니다."),
	USER_ERROR_USER_NOT_FOUND("존재하지 않은 회원입니다."),
	NOT_EXITS_PAYMENT_TYPE_ERROR("존재하지 않은 결제타입으로 결제가 진행되었습니다."),
	ORDER_ERROR_ORDER_PRICE("주문하신 주문의 금액이 형식에 맞지않습니다."),
	NOT_FOUND_PAYMENT("존재하지 않는 결제 정보 입니다."),
	IS_EMPTY_USER("회원아이디가 입력되지 않았습니다."),
	ORDER_DELETE_ERROR("주문하신 상품 삭제에 실패 하였습니다."),
	ORDER_MODIFY_ERROR("주문 수정에 실패하였습니다.");
	
	
	private final String message;
}
