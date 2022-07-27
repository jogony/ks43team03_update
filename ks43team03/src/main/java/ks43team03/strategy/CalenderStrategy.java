package ks43team03.strategy;

import java.util.Map;

import ks43team03.dto.Lesson;
import ks43team03.strategy.enumeration.CalenderStrategyName;

public interface CalenderStrategy {
	//캘린더(Lesson,)
	Map<String, Object> getData(Map<String, String> scheduleDate);
	
	CalenderStrategyName getCalendarStrategyName();
} 

