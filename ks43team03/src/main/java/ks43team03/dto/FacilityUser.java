package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FacilityUser {
	
	private String facilityCd;
	private String userId;
	private String facilityUserLevel;
	private String facilityApproveState;
	private String facilityApproveId;
	private String facilityApproveDate;
	private String facilityRegDate;
}
