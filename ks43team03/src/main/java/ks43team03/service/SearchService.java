package ks43team03.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;
import ks43team03.strategy.factory.SearchFactory;

@Service
@Transactional
public class SearchService {
	
	private static final Logger log = LoggerFactory.getLogger(SearchService.class);

	private SearchFactory searchFactory;
	
	public SearchService(SearchFactory searchFactory) {
		this.searchFactory = searchFactory;
	}
	
	public Map<String, Object> findSearch(SearchStrategyName searchCtg, Map<String, Object> searchMap, int currentPage){
	    // 이름을 전달해서 전략을 가져올 수 있다.
		log.info("SearchService 정상 작동 : {}", searchCtg);
	    SearchStrategy strategy = searchFactory.findStrategy(searchCtg);
	    
	    //전략에 의한 메서드 호출
	    return strategy.search(searchMap, currentPage);
    }
}
