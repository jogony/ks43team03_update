package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Safety {
	private String safetyCheckCd;
	private String userId;
	private String facilityCd;
	private String safetyCheckFile;
	private String safetyCheckDate;
	private String safetyCheckRegDate;
	private String safetyCheckState;
	
	private Facility facility;	
	private User user;
	private TFile tFile;
}
 

