package ks43team03.service;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ks43team03.dto.LessonOffer;

@SpringBootTest
public class LessonOfferServiceTest {
	
	@Autowired LessonOfferService lessonOfferService;
	
	@Test
	@DisplayName("LessonOffer의 전체리스트 중 종목이 수영인 체육 조회한다.")
	void offerListTest() {
		List<LessonOffer> offer = lessonOfferService.getOfferList()
			.stream()
			.filter(e -> e.getLessonSports().equals("수영"))
			.collect(Collectors.toList());
		
		Assertions.assertThat(offer.get(0).getLessonSports()).isEqualTo("수영");
	}
	
//	@Test
//	@DisplayName("LessonOffer 조건별 검색중 종목과 지역을 조회시 필터되어야한다.")
//	void getOffersSportsAndArea() {
//		
//	}
}
