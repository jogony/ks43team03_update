package ks43team03.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Boomk;
import ks43team03.dto.Facility;

@Mapper
public interface BoomkMapper {
	
	
	//즐겨찾기 조회	
	public List<Boomk> getBoomkList(String userId); 
	
	//즐겨찾기 등록
	public int addBoomk(Boomk boomk);
	
	//즐겨찾기 삭제
	public int removeBoomk(Boomk boomk);
		
	//즐겨찾기 취소 (N)
	//public int modifyBoomk(Boomk boomk);	
	
	//즐겨찾기 추가 (Y)
	//public int modifyBoomk2(Boomk boomk);	
	



	
	
	
	
}
