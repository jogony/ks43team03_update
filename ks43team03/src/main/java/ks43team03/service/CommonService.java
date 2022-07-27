package ks43team03.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Area;
import ks43team03.dto.AreaCity;
import ks43team03.dto.Order;
import ks43team03.dto.Sports;
import ks43team03.mapper.CommonMapper;

@Service
@Transactional
public class CommonService {
	CommonMapper commonMapper;
	
	private static final Logger log = LoggerFactory.getLogger(CommonService.class);

	public CommonService(CommonMapper commonMapper) {
		this.commonMapper = commonMapper;
	}
	//시/도 지역 조회
	public List<AreaCity> getAreaCityList(String areaCd) {
		List<AreaCity> areaCity = commonMapper.getAreaCityList(areaCd);
		return areaCity;
	}//시/군/구 조회
	public List<Area> getAreaList() {
		List<Area> area = commonMapper.getAreaList();
		return area;
	}// 스포츠 종목 조회
	public List<Sports> getSportsList() {
		log.info("___________LessonService/getSportsList_____________");
		List<Sports> sportsList = commonMapper.getSportsList();
		return sportsList;
	}
	//예약 하기
	public int setRservation(Map<String, String> reservationData) {
		int result = 0;
		String reservationCheck = commonMapper.reservationCheck(reservationData);
		if(reservationCheck == null) {
			commonMapper.setReservation(reservationData);
			result = 1;
		}
		return result;
	}
	//상품 구매 여부 조회
	public Order goodsOrderCheck(Map<String, String> paramMap) {
		
		Order result = commonMapper.goodsOrderCheck(paramMap);
		return result;
	}

}
