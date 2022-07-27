package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {

	private String facilityCd;
	private String level;
	private String dateAfter;
	private String dateBefore;
	private String facilityName;
}
