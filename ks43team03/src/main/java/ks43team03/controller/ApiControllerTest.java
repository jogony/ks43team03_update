package ks43team03.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ks43team03.dto.Area;
import ks43team03.dto.AreaCity;
import ks43team03.dto.FacilityGoods;
import ks43team03.service.CommonService;

@RestController
public class ApiControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(ApiControllerTest.class);
	CommonService commonService;
	
	public ApiControllerTest(CommonService commonService) {
		this.commonService = commonService;
	}
	

	@PostMapping("/api/goods")
	public FacilityGoods addGoodsCode(@RequestBody FacilityGoods facilityGoods) {

		log.info("facilityGoods : {}", facilityGoods);

		return facilityGoods;
	}
	
	@PostMapping("/api/area")
	public List<Area> getAreaList() {
		List<Area> area = commonService.getAreaList();
		
		log.info("area : {}", area);
		
		return area;
	}
	
	@PostMapping("/api/areaCity")
	public List<AreaCity> getAreaCityList(@RequestBody AreaCity areaCity) {
		List<AreaCity> area = commonService.getAreaCityList(areaCity.getAreaCd());
		
		log.info("area : {}", area);
		
		return area;
	} 
}
