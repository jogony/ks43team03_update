package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
	private String reviewCd;
	private String payCd;
	private String goodsCtgCd;
	private String userId;
	private String reviewNm;
	private String reviewDetail;
	private String reviewScore;
	private String reviewRegDate;
	
	private Facility facility;
	private Order order;
}
