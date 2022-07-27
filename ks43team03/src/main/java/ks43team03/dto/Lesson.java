package ks43team03.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Lesson {
	private String lessonCd;
	private String lessonDivision;
	private String facilityGoodsCd;
	private String facilityCd;
	private String goodsCtgCd;
	private String sportsCd;
	private String sportsName;
	private String userId;
	private String lessonName;
	private String lessonDetail;
	private String lessonTotalMember;
	private String lessonMember;
	private String lessonStartDate;
	private String lessonTime;
	private String lessonEndDate;
	private String lessonStartTime;
	private String lessonEndTime;
	private String lessonCount;
	private String lessonPrice;
	private String lessonRegDate;
	private String lessonState;
	
	private Sports 					sports;
	private Facility 				facility;
	private AreaCity 				areaCity;
	private AreaCityTown 			areaCityTown;
	private FacilityGoods 			facilityGoods;
	private List<Map<String, String>> 	relFileWithGoods;
	private List<Reservation> 		reservation;
	private List<TFile> 			tFile;
}
