package ks43team03.dto.type;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GoodsType {
	MAIN(""),
	LESSON("lesson"),
	PASS("pass"),
	STADIUM("stadium");
	
	GoodsType(String code){
		this.code = code;
	}
	
	
	private final String code;
	
	@JsonCreator
	public static GoodsType fromValue(String value) {
		return Arrays.stream(GoodsType.values())
				.filter(e -> e.getCode().equalsIgnoreCase(value))
				.findFirst()
				.orElse(MAIN);
	}
	
	public String getCode() {
		return code;
	}
}
