package ks43team03.strategy;

import java.util.Map;

import ks43team03.strategy.enumeration.AddReviewStrategyName;

public interface ReviewStrategy {

	Map<String, Object> addReview(Map<String, Object> searchMap, int currentPage);
	
	AddReviewStrategyName getReviewStrategyName();
}
