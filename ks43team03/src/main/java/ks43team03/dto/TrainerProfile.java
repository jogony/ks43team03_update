package ks43team03.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainerProfile {
	private String trainerCd;
	private String userId;
	private String trainerNickname;
	private String trainerRegDate;
	
	private User user;
	private List<TrainerCareer> trainerCareerList;
	private List<TrainerLicense> trainerLicenseList;
	private List<TFile> tFile;
}
