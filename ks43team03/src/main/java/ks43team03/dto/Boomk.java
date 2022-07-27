package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Boomk {
	private String boomkFacilityCd;
	private String facilityCd;
	private String userId;
	private String boomkRegDate;
	private String boomkFacilityState;
	private String boomkUpdateDate;

	private Facility facility;
}
 

