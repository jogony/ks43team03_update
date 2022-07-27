package ks43team03.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Facility {
	private String facilityCd;
	private String mainCtgCd;	
	private String areaCd;
	private String cityCd;
	private String townCd;
	private String facilityUseCd;
	private String userId;
	private String facilityNm;
	private String facilityAddr;
	private String facilityTell;
	private String facilityPostNum;
	private String businessNum;
	private String facilityDetailAddr;
	private String facilityRegDate;
	private String facilityDetail;
	
	
	private MainCtg mainCtg;
	private FacilityUse facilityUse;
	private Area area;
	private AreaCity areaCity;
	private AreaCityTown areaCityTown;
	private FacilityUser facilityUser;
	private List<TFile> tFile;
	private List<Map<String, String>> relFileWithFacility;
	
}
