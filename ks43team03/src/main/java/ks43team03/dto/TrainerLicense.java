package ks43team03.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class TrainerLicense {
	private String trainerLicenseCd;
	private String trainerCd;
	private String trainerLicense;
	private String trainerLicenseFile;
	private String trainerLicenseRegDate;
	
	private MultipartFile[] trainerLicenseFiles;
	private List<TrainerLicense> trainerLicenseList;
}
