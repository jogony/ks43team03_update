package ks43team03.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.BookingDto;

@Mapper
public interface BookingMapper {

	
	//예약
	public int addBooking(BookingDto booking);
	
	//시설 내 상품코드로 예약 조회
	public List<BookingDto> getbookingListByCd(String facilityGoodsCd);
	
}
