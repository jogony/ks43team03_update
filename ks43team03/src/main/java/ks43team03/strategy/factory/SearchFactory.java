package ks43team03.strategy.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class SearchFactory {
	
	private static final Logger log = LoggerFactory.getLogger(SearchFactory.class);

	private Map<SearchStrategyName, SearchStrategy> strategies;
  
	@Autowired
	public SearchFactory(Set<SearchStrategy> strategySet) {
		//SearchStrategy 인터페이스로 부터 구현을 명령 받은 모든 클래스를 bean에서 찾아서 가져 옴
		log.info("searchStrategy 바인딩 : {}", strategySet);
		createStrategy(strategySet);
	}
  
	public SearchStrategy findStrategy(SearchStrategyName strategyName) {

		return strategies.get(strategyName);
	}
	private void createStrategy(Set<SearchStrategy> strategySet) {
		strategies = new HashMap<SearchStrategyName, SearchStrategy>();
		strategySet.forEach( 
		SearchStrategy ->strategies.put(SearchStrategy.getSearchStrategyName(), SearchStrategy));
		strategySet.forEach( s -> log.info("SearchStrategy.getSearchStrategyName : {}", s.getSearchStrategyName()));
		
		log.info("전략 객체 생성 성공");
	}
}
