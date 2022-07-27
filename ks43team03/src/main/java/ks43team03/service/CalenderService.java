package ks43team03.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Lesson;
import ks43team03.strategy.CalenderStrategy;
import ks43team03.strategy.enumeration.CalenderStrategyName;
import ks43team03.strategy.factory.CalenderFactory;

@Service
@Transactional
public class CalenderService {
	
	private CalenderFactory calenderFactory;
	
	private static final Logger log = LoggerFactory.getLogger(CalenderService.class);

	public CalenderService(CalenderFactory calenderFactory) {
		this.calenderFactory = calenderFactory;
	}

	public Map<String, Object> findSearch(Map<String, String> scheduleDate) {
		CalenderStrategyName scheduleName = CalenderStrategyName.valueOf(scheduleDate.get("scheduleCtg"));
		CalenderStrategy strategy = calenderFactory.findStrategy(scheduleName);
		log.info("findSearch___");
		return strategy.getData(scheduleDate);
	}

}
