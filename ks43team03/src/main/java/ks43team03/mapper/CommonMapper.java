package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Area;
import ks43team03.dto.AreaCity;
import ks43team03.dto.Order;
import ks43team03.dto.Sports;

@Mapper
public interface CommonMapper {


	
	/**
	 * 사용법 
	 * @param colunm  테이블의 pk칼럼명과 일치해야된다. 예를들어 
	 * pk 칼럼명이 order_cd << 라면 order_cd입력한다. 입력이 끝나면 order_cd_1 ,order_cd_2, order_cd_3 << 이런식으로 출력
	 * 불일치할경우  SQL syntax 에러 발생한다.
	 * @param tableName 테이블 명을 입력한다. 
	 * @return pk의 값을 리턴한다.
	 * 하지만 문제점이 있을 수 있음 
	 */
	
	public String createNewCode(String column,String tableName);
	
	/**
	 * SELECT
			case
			when COUNT(o.order_cd) = 0 
			then 'order_cd_1'
			else
				CONCAT('order_cd','_',MAX(CAST(SUBSTRING_INDEX(o.order_cd,'_',-1) AS UNSIGNED))+1)
			END
		FROM
			goods_order AS o
	 */
	/****
	 * 
	 * 스포츠 테이블 가져오기
	 *
	 ****/
	public List<Sports> getSportsList();
	
	/**
	 * 레슨/대관 예약 시간 확인하기
	 **/
	public String reservationCheck(Map<String, String> reservationData);
	
	/***
	 * 
	 * 어떤 도시의 시/군/구 가져오기
	 * 
	 ***/
	
	public List<AreaCity> getAreaCityList(String areaCd);
	/****
	 * 
	 * 시/도 가져오기
	 *
	 ****/
	public List<Area> getAreaList();
	
	/**
	 *레슨/대관 예약 하기
	 **/
	public int setReservation(Map<String, String> reservationData);

	public Order goodsOrderCheck(Map<String, String> paramMap);
	

}
