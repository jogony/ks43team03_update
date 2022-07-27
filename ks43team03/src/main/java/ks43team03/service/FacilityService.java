package ks43team03.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Facility;
import ks43team03.dto.FacilityUser;
import ks43team03.dto.Lesson;
import ks43team03.dto.Review;
import ks43team03.dto.Stadium;
import ks43team03.mapper.FacilityMapper;
import ks43team03.mapper.LessonMapper;
import ks43team03.mapper.ReviewMapper;
import ks43team03.mapper.StadiumMapper;


@Service
@Transactional
public class FacilityService {

	private static final Logger log = LoggerFactory.getLogger(FacilityService.class);
	
	private final FacilityMapper facilityMapper;
	private final StadiumMapper stadiumMapper;
	private final LessonMapper lessonMapper;
	private final ReviewMapper reviewMapper;
	
	
	
	public FacilityService(FacilityMapper facilityMapper, StadiumMapper stadiumMapper, LessonMapper lessonMapper,  ReviewMapper reviewMapper) {
		this.facilityMapper = facilityMapper;
		this.stadiumMapper = stadiumMapper;
		this.lessonMapper = lessonMapper;
		this.reviewMapper = reviewMapper;
		
	}

	/*시설후기갯수*/
	public int getReviewCountByCd(String facilityCd) {
		int reviewCount = reviewMapper.getReviewCountByCd(facilityCd);
		return reviewCount;
	}
	
	/*시설 후기 리스트*/
	public List<Review> getReviewList(String facilityCd){
		List<Review> reviewList = reviewMapper.getReviewList(facilityCd);
		log.info("reviewList", reviewList);

		return reviewList;
	}
	
	/*시설 가입 중복 체크*/
	public boolean isUserCheck(String userId, String facilityCd) {
		boolean result = facilityMapper.isUserCheck(userId, facilityCd);
		return result;
	}
	
	
	
	/*시설에 회원 가입*/
	public int addFacilityUser(FacilityUser facilityUser) {
		int result = facilityMapper.addFacilityUser(facilityUser);
		log.info("result", result);

		return result;
	}
	
	
	/*시설 내 구장 목록*/
	public List<Stadium> getStadiumListByCd(String facilityCd) {
		List<Stadium> stadiumListByCd = stadiumMapper.getStadiumListByCd(facilityCd);
		
		log.info("stadiumListByCd", stadiumListByCd);
		return stadiumListByCd;
	}
	
	/*시설 내 레슨 목록*/
	public List<Lesson> getLessonList(String facilityCd) {
		List<Lesson> lessonList = lessonMapper.getFacilityLessonList(facilityCd);
		log.info("lessonList", lessonList);

		
		return lessonList;	
		}
	
	/*시설 상세 정보 조회*/
	public Facility getFacilityDetail(String facilityCd) {
		Facility facilityDetail = facilityMapper.getFacilityInfoByCd(facilityCd);
		log.info("facilityDetail", facilityDetail);
		
		return facilityDetail;
	}
	
	
	
}
