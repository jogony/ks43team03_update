package ks43team03.strategy.calender;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.Lesson;
import ks43team03.mapper.LessonMapper;
import ks43team03.strategy.CalenderStrategy;
import ks43team03.strategy.enumeration.CalenderStrategyName;

@Component
public class LessonCalendar
implements CalenderStrategy{
	
	private static final Logger log = LoggerFactory.getLogger(LessonCalendar.class);
	private LessonMapper lessonMapper;
	
	public LessonCalendar(LessonMapper lessonMapper) {
		this.lessonMapper = lessonMapper;
	}
	
	@Override
	//@ResponseBody
	public Map<String, Object> getData(Map<String, String> DateMap) {
		log.info("동작!");
		log.info("DateMap : {}", DateMap);
		Map<String, Object> dateMap = new HashMap<>();
		Lesson lesson = lessonMapper.getReservation(DateMap);
		log.info(lesson.getLessonEndDate());
		dateMap.put("scheduleDate", lesson);
		return dateMap;
	}

	@Override
	public CalenderStrategyName getCalendarStrategyName() {
		// TODO Auto-generated method stub
		return CalenderStrategyName.LessonReservation;
	}

}
