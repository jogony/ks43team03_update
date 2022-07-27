package ks43team03.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatus {
	DONE("결제 완료"),
	CANCELED("결제취소"),
	EXPIRED("유효시간이 지나 결제 취소"),
	WAITING_FOR_DEPOSIT("가상계좌 입금 대기 중");
	
	
	private final String message;
}
