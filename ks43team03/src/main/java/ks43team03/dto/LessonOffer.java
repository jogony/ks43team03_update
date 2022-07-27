package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class LessonOffer {

	
	private String lessonOfferCd; //레슨 구인 코드
	private String facilityCd; //시설 대표 코드		
	private String lessonCd;//레슨 대표 코드
	private String userId;//레슨 구인 등록자
	private String lessonSports;//종목
	private String offerTitle;//공고제목
	private String offerDetail;//상세정보
	private String offerFile; //첨부파일
	private String offerEndDate;//모집 마감 일자
	private String offerState;//모집상태
	private String offerRegDate;//등록일자
	private String offerUpdateDate;//업데이트 일자	
	
	
	private Lesson lesson;
	private Facility facility;
	
	
}
