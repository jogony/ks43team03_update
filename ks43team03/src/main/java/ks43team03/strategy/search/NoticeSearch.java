package ks43team03.strategy.search;

import java.util.Map;

import org.springframework.stereotype.Component;

import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class NoticeSearch 
implements SearchStrategy{

	@Override
	public Map<String, Object> search(Map<String, Object> searchMap, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchStrategyName getSearchStrategyName() {
		// TODO Auto-generated method stub
		return SearchStrategyName.NoticeSearch;
	}
}
