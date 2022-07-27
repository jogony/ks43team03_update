package ks43team03.strategy.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.strategy.CalenderStrategy;
import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.CalenderStrategyName;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class CalenderFactory {
	
	private static final Logger log = LoggerFactory.getLogger(CalenderFactory.class);
	private Map<CalenderStrategyName, CalenderStrategy> strategies;
	
	public CalenderFactory(Set<CalenderStrategy> strategySet) {
		log.info("searchStrategy 바인딩 : {}", strategySet);
		createStrategy(strategySet);
	}
	public CalenderStrategy findStrategy(CalenderStrategyName strategyName) {

		return strategies.get(strategyName);
	}
	
	private void createStrategy(Set<CalenderStrategy> strategySet) {
		strategies = new HashMap<CalenderStrategyName, CalenderStrategy>();
		strategySet.forEach(
		CalenderStrategy ->strategies.put(CalenderStrategy.getCalendarStrategyName(), CalenderStrategy));
		strategySet.forEach( s -> log.info("CalenderStrategy.getCalenderStrategyName : {}", s.getCalendarStrategyName()));
		
		log.info("전략 객체 생성 성공");
	}
}
