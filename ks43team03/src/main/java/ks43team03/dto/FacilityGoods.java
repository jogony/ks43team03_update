package ks43team03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class FacilityGoods {
	
	/**
	 * 시설 내 상품카테고리 코드	시설 대표 코드	카테고리 코드	종목 코드	등록자 아이디	등록일자	상품 활성화 상태
	facility_goods_cd	facility_cd	goods_ctg_cd	sports_cd	user_id	facility_goods_reg_date	facility_goods_state
	 */
	
	private String facilityGoodsCd;
	private String facilityCd; // 시설대표코드
	private String goodsCtgCd; //카테고리코드
	private String sportsCd; //종목코드
	private String userId; //등록자아이디
	private String facilityGoodsRegDate; //등록날짜
	private String faclityGoodsState; //상품활성화상태

	
	private Pass pass;
	private Lesson lesson;
	private Stadium stadium;
	
	private Facility facility;
	
	private Sports sports;
	
	
	public FacilityGoods() {}
	
	
	@Builder
	public FacilityGoods(String facilityCd, String goodsCtgCd,String userId, String sportsCd) {
		this.facilityCd = facilityCd;
		this.goodsCtgCd = goodsCtgCd;
		this.userId = userId;
		this.sportsCd = sportsCd;
	}
	
	public ResponseGoods passDto() {
		return ResponseGoods.builder()
				.facilityGoods(this)
				.goodsName(this.pass.getPassNm())
				.price(this.pass.getPassPrice())
				.build();
	}
	
	
	public ResponseGoods lessonDto() {
		return ResponseGoods.builder()
				.facilityGoods(this)
				.goodsName(this.lesson.getLessonName())
				.price(Integer.parseInt(this.lesson.getLessonPrice()))
				.build();
	}
	
	/**
	 * 
	 * 대관 가격 수정해야됨 시간대별로 다르기 때문에
	 *
	 */
	public ResponseGoods stadiumDto() {
		return ResponseGoods.builder()
				.facilityGoods(this)
				.goodsName(this.stadium.getFacilityStadiumNm())
				.price(Integer.parseInt(this.stadium.getStadiumPrice().getDayPrice()))
				.build();
	}
	
}
