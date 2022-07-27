package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class StadiumPrice {

	private String stadiumPriceCd;
	private String facilityStadiumCd;
	private String sportsCd;
	private String dayPrice;
	private String nightPrice;
	private String holPrice;
	
}
