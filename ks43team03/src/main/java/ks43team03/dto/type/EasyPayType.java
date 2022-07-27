package ks43team03.dto.type;

import lombok.Getter;

@Getter
public enum EasyPayType {
	TOSSPAY("토스페이"),
	SAMSUNGPAY("삼성페이"),
	KAKAOPAY("카카오페이"),
	PAYCO("페이코"),
	LGPAY("LG페이"),
	SSG("SSG페이"),
	LPAY("엘페이");
	EasyPayType(String name) {
		this.name = name;
	}
	
	private final String name;
}
