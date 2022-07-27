package ks43team03.dto;

import java.util.Map;

import ks43team03.strategy.SearchStrategy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
	
	Map<String, Object> searchStrategy;
	private String searchKey;
	private String searchValue;
	
	public Search(Map<String, Object> searchStrategy) {
		this.searchStrategy = searchStrategy;
	}
}
