package ks43team03.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import ks43team03.dto.type.GoodsType;
import lombok.Getter;

@Getter
public class GoodsCategoryDto {

	private GoodsType code;
	private String userId;
	
	public GoodsCategoryDto(@JsonProperty("code") GoodsType code,
			@JsonProperty("userId") String userId) {
		this.code = code;
		this.userId = userId;
	}
	
//	public GoodsCategoryDto( GoodsType code,
//			@JsonProperty("userId") String userId) {
//		this.code = code;
//		this.userId = userId;
//	}
}
