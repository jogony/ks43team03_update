package ks43team03.strategy.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.Facility;
import ks43team03.mapper.FacilityMapper;
import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class FacilitySearch 
implements SearchStrategy{
	
	private static final Logger log = LoggerFactory.getLogger(FacilitySearch.class);
	private FacilityMapper facilityMapper;
	
	
	public FacilitySearch(FacilityMapper facilityMapper) {
		this.facilityMapper = facilityMapper;
		
	}
	
	@Override
	public Map<String, Object> search(Map<String, Object> facilityMap, int currentPage) {
		log.info("facility 검색 정상 작동");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int rowPerPage = 9;
		int startPageNum = 1;
		int endPageNum = 10;
		
		double rowCount = facilityMapper.getFacilityCount();
		
		int lastPage = (int)Math.ceil(rowCount/rowPerPage);
		
		int startRow = (currentPage - 1) * rowPerPage;
		
		if(lastPage > 10) {
			if(currentPage >= 6) {
				startPageNum = currentPage - 4;
				endPageNum = currentPage + 5;
				
				if(endPageNum >= lastPage) {
					startPageNum = lastPage - 9;
					endPageNum = lastPage;
				}
			}
		}else {
			endPageNum = lastPage;
		}
		
		
		
		resultMap.put("startRow", startRow);
		resultMap.put("rowPerPage", rowPerPage);
		resultMap.put("lastPage", 			lastPage);
		resultMap.put("startPageNum",		startPageNum);
		resultMap.put("endPageNum",			endPageNum);
		
		facilityMap.put("startRow", startRow);
		facilityMap.put("rowPerPage", rowPerPage);
		log.info("facilityMap : {}", facilityMap);
		List<Facility> searchList = facilityMapper.getFacilityList(facilityMap);
		
		resultMap.put("searchList",	searchList);
		resultMap.put("path", "facility/facilityList");
		
		return resultMap;
	}

	@Override
	public SearchStrategyName getSearchStrategyName() {
		return SearchStrategyName.FacilitySearch;
	}



}
