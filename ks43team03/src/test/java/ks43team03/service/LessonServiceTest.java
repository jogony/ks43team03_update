package ks43team03.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ks43team03.dto.Lesson;

@SpringBootTest
public class LessonServiceTest {

	@Autowired
	LessonService lessonService;
	
	@Test
	@DisplayName("시설 내에 레슨 리스트 검색을 한다.")
	void Test() {
		String facilityCd = "ss_35011740_01";
		List<Lesson> lesson = lessonService.getfacilityLessonList(facilityCd);
		System.out.println(lesson);
	}
}
