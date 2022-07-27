package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Review;


@Mapper
public interface ReviewMapper {
	
	//후기 삭제
	public int removeReview(String reviewCd, String userId);
	
	//후기코드로 후기 조회
	public Review reviewInfoById(String userId);
	
	//결제한 회원만 리뷰작성(시설별)
	public boolean isOrderCheck(String userId, String facilityCd);
	
	//시설 상품 코드로 후기 조회
	public List<Review> getReviewListByCd(String facilityGoodsCd);

	//시설 후기 갯수
	public int getReviewCountByCd(String facilityCd);
	
	//시설 후기 
	public List<Review> getReviewList(String facilityCd);
	
	//시설에후기등록
	public int addReview(Review review);
	
	//시설관리자가 본인 후기 조회
	public List<Review> getAdminReviewListById(String userId);
	
	//후기 테이블 총 row 수
	public int getReviewCount();
	
	//후기
	public List<Map<String, Object>> getAdminReviewList(Map<String, Object> paramMap);
}
