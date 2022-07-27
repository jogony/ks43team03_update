package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Lesson;

@Mapper
public interface LessonMapper {

	//시설 내 레슨 조회하기
	public List<Lesson> getFacilityLessonList(String facilityCd);
	
	//레슨 등록하기
	public int addLesson(Lesson lesson);
	
	//레슨 코드를 파라미터로 주고 정보를 받아오기
	public Lesson getLessonInfoByCd(String lessonCd);
	
	//유저 화면에 뿌려줄 레슨리스트 가져오기
	public List<Lesson> getLessonListForUser(Map<String, Object> lessonMap);
	
	public int modifyLesson(Lesson lesson);
	//레슨 목록을 위한 개수 구하기
	public int getLessonCount();
	//레슨 예약 목록 가져오기
	public Lesson getReservation(Map<String, String> lessonDate);

}
