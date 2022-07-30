package ks43team03.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.ResponseGoods;
import ks43team03.exception.CustomException;
import ks43team03.exception.ErrorMessage;
import ks43team03.mapper.FacilityGoodsMapper;
import ks43team03.mapper.FacilityMapper;

@Service
@Transactional
public class FacilityGoodsService {

	
	private static final Logger log = LoggerFactory.getLogger(FacilityGoodsService.class);

	private final FacilityGoodsMapper facilityGoodsMapper;
	private final FacilityMapper facilityMapper;
	
	public FacilityGoodsService(FacilityGoodsMapper facilityGoodsMapper,FacilityMapper facilityMapper) {
		this.facilityGoodsMapper = facilityGoodsMapper;
		this.facilityMapper = facilityMapper;
	}
	
	/**
	 * 
	 * @param facilityGoods  lesson pass stadium 레슨 이용권 대관
	 * @return 
	 */
	
	public String addGoodsCode(FacilityGoods facilityGoods) {
		log.info("addPass에서 받은 값 : {}", facilityGoods);
		// 시설조회
		facilityMapper.getFacilityInfoByCd(facilityGoods.getFacilityGoodsCd());
		facilityGoodsMapper.addFacilityGoods(facilityGoods);
		return facilityGoods.getFacilityGoodsCd();
		
	}
	
	// 상폼하나 조회 
	public ResponseGoods getFacilityGoodsCd(String facilityGoodsCd, String goodsCtgCd ) {

		switch (goodsCtgCd) {
		case "lesson":
			FacilityGoods lessonGoods = facilityGoodsMapper.getFacilityGoodsLesson(facilityGoodsCd);
			return lessonGoods.lessonDto();
		case "stadium":
			FacilityGoods stadiumGoods = facilityGoodsMapper.getFacilityGoodsStadium(facilityGoodsCd);
			return stadiumGoods.stadiumDto();
		case "pass":
			FacilityGoods passGoods = facilityGoodsMapper.getFacilityGoodsPass(facilityGoodsCd);
			return passGoods.passDto();
		}
		throw new CustomException(ErrorMessage.NOT_FOUND_GOODS);
	}
	
}
