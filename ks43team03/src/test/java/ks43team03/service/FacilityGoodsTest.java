package ks43team03.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.ResponseGoods;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FacilityGoodsTest {

	@Autowired
	private FacilityGoodsService fGoodserv;
	
	
	@Test
	void addFacilityGoods() {
		FacilityGoods fg = new FacilityGoods();
		
		fg.setFacilityCd("ss_35011600_04");
		fg.setGoodsCtgCd("lesson");
		fg.setSportsCd("sp04");
		fg.setUserId("id002");
		
		fGoodserv.addGoodsCode(fg);
		
		System.out.println(fg.getFacilityGoodsCd());
		log.info("결과값은 : {}",fg.getFacilityGoodsCd());
	}
	
	@Test
	@DisplayName("상품코드로 이용권인 상품을 조회 한다")
	void getPassGoods() {
		String goodsCode = "facility_goods_cd_24";
		
		ResponseGoods goods = fGoodserv.getFacilityGoodsCd(goodsCode,"pass");
		
		Assertions.assertThat(goods.getFacilityGoods().getFacilityGoodsCd()).isEqualTo(goodsCode);
	}
	
	
}
