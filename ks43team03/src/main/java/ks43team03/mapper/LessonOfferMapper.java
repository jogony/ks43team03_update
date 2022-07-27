package ks43team03.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.LessonOffer;

@Mapper
public interface LessonOfferMapper {

	// LessonOffer List 
	public List<LessonOffer> getOfferList();
	
	// 레슨 offer 지역별 종목별 조회
	public List<LessonOffer> getLessonOfferCityOrSports(String areaCd,String sportsName);
	
	//레슨_offer lesson_offer_cd로조회
	public Optional<LessonOffer> getLessonOfferByLessonOfferCd(String lessonOfferCd);
	
	
	//lessonOffer 삭제
	public int removeLessonOffer(String lessonOfferCd);
	
	//lessonOffer 수정
	public int modifyLessonOffer(LessonOffer lessonOffer);
	
	//lessonOffer 등록
	public int addLessonOffer(LessonOffer lessonOffer);
	
}
