package ks43team03.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Safety;


@Mapper
public interface SafetyMapper {
	
	//안전점검 등록
	public int addSafety(Safety safety);

	//관리자의 안전점검 등록 시설 목록 조회(전체)(페이징)
	public List<Map<String, Object>> getSafetyList(Map<String, Object> paramMap);	
	
	//안전점검 등록 시설 목록 총 row(튜플) 수
	public int getSafetyListCount();	
	
	//시설 관리자의 안전점검 등록 목록 조회
	public List<Safety> getSafetyListById(String userId);
	
	//안전점검 등록 정보 수정
	public int modifySafety(Safety safety);
	
	//안전점검 등록코드별 시설 수정
	public Safety getSafetyInfoByCd(String safetyCheckCd);

	
	//안전점검 등록 정보 삭제
	public int removeSafety(Safety safety);

	//시설 관리자의 안전점검 등록 정보 삭제
	public int removeSafety2(Safety safety);
	
	
	
	//관리자의 안전점검 결과 목록 조회(전체)(페이징)
	public List<Map<String, Object>> getSafetyResultList(Map<String, Object> paramMap);
	
	//안전점검 결과 목록 총 row(튜플) 수
	public int getSafetyResultListCount();	

	//시설 관리자의 안전점검 결과 목록 조회 
	public List<Safety> getSafetyResultListById(String userId);

	
	
	
	
}
