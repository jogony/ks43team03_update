package ks43team03.strategy.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ks43team03.strategy.ReviewStrategy;
import ks43team03.strategy.enumeration.AddReviewStrategyName;


@Component
public class ReviewFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ReviewFactory.class);

	private Map<AddReviewStrategyName, ReviewStrategy> strategies;
  
	@Autowired
	public ReviewFactory(Set<ReviewStrategy> strategySet) {
		//SearchStrategy 인터페이스로 부터 구현을 명령 받은 모든 클래스를 bean에서 찾아서 가져 옴
		log.info("searchStrategy 바인딩 : {}", strategySet);
		createStrategy(strategySet);
	}
  
	public ReviewStrategy findStrategy(AddReviewStrategyName strategyName) {

		return strategies.get(strategyName);
	}
	private void createStrategy(Set<ReviewStrategy> strategySet) {
		strategies = new HashMap<AddReviewStrategyName, ReviewStrategy>();
		strategySet.forEach( 
		ReviewStrategy ->strategies.put(ReviewStrategy.getReviewStrategyName(), ReviewStrategy));
		strategySet.forEach( s -> log.info("SearchStrategy.getSearchStrategyName : {}", s.getReviewStrategyName()));
		
		log.info("전략 객체 생성 성공");
	}
}
