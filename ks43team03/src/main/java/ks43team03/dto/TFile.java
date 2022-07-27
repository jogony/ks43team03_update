package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TFile {
	private String fileCd;
	private String originalFileName;
	private String storedFilePath;
	private String reFileName;
	private int	   fileSize;
	private String uploaderId;
	private String uploadDate;
	private String updatorId;
	private String updatedDate;
	private String representImg;
}