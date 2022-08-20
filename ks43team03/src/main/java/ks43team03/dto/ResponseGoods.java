package ks43team03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGoods {
	private FacilityGoods facilityGoods;
	
	private String goodsName;
	private int price;
	
	@Builder
	public ResponseGoods(FacilityGoods facilityGoods, String goodsName, int price) {
		this.facilityGoods = facilityGoods;
		this.goodsName = goodsName;
		this.price = price;
	}
	
	
	public ResponseGoods(){}
	
}
