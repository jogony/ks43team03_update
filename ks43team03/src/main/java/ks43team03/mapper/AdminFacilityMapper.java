package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Area;
import ks43team03.dto.AreaCity;
import ks43team03.dto.AreaCityTown;
import ks43team03.dto.Facility;
import ks43team03.dto.FacilityUse;
import ks43team03.dto.MainCtg;

@Mapper
public interface AdminFacilityMapper {
	

	
	//시설 검색
	public List<Map<String, Object>> getSearchFacilityList(Map<String, Object> paramMap);
	
	//아이디별 시설 목록 조회
	public List<Facility> getAdminFacilityListById(String userId);
	
	//판매자가 등록한 시설삭제
	public int removeFacilityByCd(String facilityCd);
	
	//시설 수정
	public int modifyFacility(Facility facility);
	
	//시설코드별 시설 상세정보조회
	public Facility getAdminFacilityInfoByCd(String facilityCd);
	
	//시도 조회
	public List<Area> getAreaList();
	
	//시군구 조회
	public List<AreaCity> getAreaCityList(String areaCd);
	
	//읍면동 조회
	public List<AreaCityTown> getAreaCityTownList(String cityCd);
	
	//시설카테고리조회
	public List<MainCtg> getMainCtgList();
	
	//시설 용도 조회
	public List<FacilityUse> getFacilityUseList();
	
	//시설등록
	public int addFacility(Facility facility);
	
	//시설 테이블 총 row(튜플) 수
	public int getFacilityCount();
	
	//시설목록조회
	public List<Map<String, Object>> getAdminFacilityList(Map<String, Object> paramMap);




}
