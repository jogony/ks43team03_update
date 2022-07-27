package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayMethod {
	/**
	 *  결제 수단 
	 */
	
	
	private String payMethodCd; //결제수단코드
	private String payMethodNm;//결제 수단명
	private String payMethodfirm;//결제대행업체
}
