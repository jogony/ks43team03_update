package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingDto {


	
	private String bookingCd; //예약 신청 코드
	private String facilityCd;//시설 대표 코드
	private String facilityGoodsCd; //시설 내 상품카테고리 코드
	private String userId;//예약 회원 아이디
	private String goodsCtgCd;//카테고리 코드
	private String bookingDate;//예약 날짜
	private String bookingStartTime;	//예약 시작 시간
	private String bookingEndTime;//예약 끝나는 시간
	private String bookingState; //예약 상태
	private String bookingPrice; //금액
	private String bookingRegDate;	//등록 일자
	
}
