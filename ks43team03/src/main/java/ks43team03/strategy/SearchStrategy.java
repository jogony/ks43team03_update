package ks43team03.strategy;

import java.util.Map;

import ks43team03.strategy.enumeration.SearchStrategyName;

public interface SearchStrategy {
	//트레이너, 시설, 레슨, 대관 통합 검색
	Map<String, Object> search(Map<String, Object> searchMap, int currentPage);
	
	SearchStrategyName getSearchStrategyName();
} 

