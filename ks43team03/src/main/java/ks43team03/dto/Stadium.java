package ks43team03.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Stadium {
	private String facilityStadiumCd;
	private String facilityGoodsCd;
	private String facilityCd;
	private String goodsCtgCd;
	private String sportsCd;
	private String userId;
	private String facilityStadiumNm;
	private String facilityStadiumRegDate;
	private String facilityStadiumDetail;
	
	
	private Facility 					facility;
	private Sports 						sports;
	private StadiumPrice 				stadiumPrice;
	private FacilityGoods 				facilityGoods;
	private List<Map<String, String>> 	relFileWithGoods;
	private List<TFile> 				tFile;
	private List<Reservation> 			Reservation;
	private FacilityUser 				facilityUser;
	
}
