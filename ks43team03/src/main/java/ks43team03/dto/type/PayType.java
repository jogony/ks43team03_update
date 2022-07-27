package ks43team03.dto.type;

import lombok.Getter;

@Getter
public enum PayType {

	CARD("카드"),
	VIRTUAL_ACCOUNT("가상계좌");
//	MOBILE_PHONE("휴대폰"),
//	TRANSFER("계좌이체")
	
	PayType(String name){
		this.name = name;
	}
	
	private final String name;
}
