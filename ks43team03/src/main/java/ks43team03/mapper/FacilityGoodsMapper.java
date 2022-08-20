package ks43team03.mapper;



import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.Lesson;
import ks43team03.dto.Stadium;

@Mapper
public interface FacilityGoodsMapper {
	
	
	public void addFacilityGoods(FacilityGoods facilityGoods);
	
	
	// 상폼하나 조회 
	public Optional<FacilityGoods> getFacilityGoodsCd(String facilityGoodsCd);
	
	
	public FacilityGoods getFacilityGoodsPass(String facilityGoodsCd);
	
	public FacilityGoods getFacilityGoodsLesson(String facilityGoodsCd);
	
	public FacilityGoods getFacilityGoodsStadium(String facilityGoodsCd);
	
	// 수정하기
	public int modifyLesson(Lesson lesson);

	
	public int modifyStadium(Stadium stadium);
	
	
}
