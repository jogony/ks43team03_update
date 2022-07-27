package ks43team03.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Pass;


@Mapper
public interface PassMapper {


	// 이용권 등록
	public void addPass(Pass pass);
	// 관리자 이용권 리스트
	public List<Pass> getPassAll();
	// 이용권정보
	public Optional<Pass> getPassDetail(String passCd, String facilityGoodsCd);
	// 시설 관리자 이용권 리스트
	public List<Pass>getPassListOfFacility(String facilityCd);
	// 이용권정보
	public Pass getPassByPassCd(String passCd);
	
	// 이용권 수정
	public int modifyPass(Pass pass);
	
	
	//이용권삭제
	public int removePass(String passCd);
}
