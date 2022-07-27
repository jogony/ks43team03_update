package ks43team03.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.Pass;
import ks43team03.exception.CustomException;
import ks43team03.exception.ErrorMessage;
import ks43team03.mapper.FacilityGoodsMapper;
import ks43team03.mapper.PassMapper;

@Service
@Transactional
public class PassService {

	private final FacilityGoodsMapper facilityGoodsMapper;
	private final PassMapper passMapper;
	
	public PassService(FacilityGoodsMapper facilityGoodsMapper,PassMapper passMapper) {
		this.facilityGoodsMapper = facilityGoodsMapper;
		this.passMapper = passMapper;
	}
	
	/**
	 * 
	 * @param pass
	 * @return 상품코드 pk값
	 * 이용권 등록 메소드
	 * 
	 */
	public String addPass(Pass pass) {
		
		FacilityGoods facilityGoods = pass.toFacilityGoods();
		
		facilityGoodsMapper.addFacilityGoods(facilityGoods);
		
		pass.setFacilityGoodsCd(facilityGoods.getFacilityGoodsCd());
		
		passMapper.addPass(pass);
		
		return facilityGoods.getFacilityGoodsCd();
	}
	
	/**
	 * 이용권 전체리스트 관리자 페이지 
	 * @return 이용권 리스트
	 */
	@Transactional(readOnly = true)
	public List<Pass> getPassAll(){
		return passMapper.getPassAll();
	}
	
	/**
	 * 
	 * @param 이용권코드 , 상품코드 
	 * @return  이용권의 정보
	 */
	@Transactional(readOnly = true)
	public Pass getPassDetail(String passCd, String facilityGoodsCd) {
		Pass pass = passMapper.getPassDetail(passCd, facilityGoodsCd)
				.orElseThrow(()-> new CustomException(ErrorMessage.GOODS_ERROR_NOT_FOUND_PASS));
		
		return pass;
	}
	
	/**
	 * 
	 * @param 시설 코드 
	 * @return 시설의 이용권리스트
	 */
	@Transactional(readOnly = true)
	public List<Pass> getPassListOfFacility(String facilityCd){
		
		List<Pass>passList = passMapper.getPassListOfFacility(facilityCd);
		return passList;
	}
	
	/**
	 * 
	 * @param pass
	 * 이용권 수정 메소드
	 */
	public int modifyPass(Pass pass) {
		int passResult = passMapper.modifyPass(pass);
		return passResult;
	}
	
	
	
	/**
	 * 
	 * @param passCd
	 * 이용권 삭제 메소드
	 */
	public int removePass(String passCd) {
		return passMapper.removePass(passCd);
		
	}
	
	
//	public Pass getPassByPassCd(String passCd) {
//		Pass pass = passMapper.getPassByPassCd(passCd);
//		return pass;
//	}
	
	
	
}
