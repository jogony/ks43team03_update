package ks43team03.strategy.calender;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.Stadium;
import ks43team03.mapper.StadiumMapper;
import ks43team03.strategy.CalenderStrategy;
import ks43team03.strategy.enumeration.CalenderStrategyName;

@Component
public class StadiumCalendar
implements CalenderStrategy{
	
	private static final Logger log = LoggerFactory.getLogger(StadiumCalendar.class);
	private StadiumMapper stadiumMapper;
	
	public StadiumCalendar(StadiumMapper stadiumMapper) {
		this.stadiumMapper = stadiumMapper;
	}
	
	@Override
	//@ResponseBody
	public Map<String, Object> getData(Map<String, String> DateMap) {
		log.info("동작!");
		log.info("DateMap : {}", DateMap);
		Map<String, Object> dateMap = new HashMap<>();
		Stadium stadium = stadiumMapper.getReservation(DateMap);
		dateMap.put("scheduleDate", stadium);
		return dateMap;
	}

	@Override
	public CalenderStrategyName getCalendarStrategyName() {
		// TODO Auto-generated method stub
		return CalenderStrategyName.StadiumReservation;
	}

}
