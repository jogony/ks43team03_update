package ks43team03.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Pass{
	
	private String passCd;	
	private String facilityGoodsCd;
	private String facilityCd;
	private String goodsCtgCd;
	private String userId;
	private String passNm;
	private int passUnit;
	private int passPrice;
	private String passState;
	private String passRegDate;
	private int passEndDate;
	
	//sportscd
	private String sportsCd;
	
	private Facility facility;
	
	
	public Pass() {}
	
	public FacilityGoods toFacilityGoods() {
		return FacilityGoods.builder()
					.facilityCd(facilityCd)
					.userId(userId)
					.goodsCtgCd(goodsCtgCd)
					.sportsCd(sportsCd)
					.build();
					
	}
	
	
	
}
