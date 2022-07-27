package ks43team03.strategy.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.Stadium;
import ks43team03.mapper.StadiumMapper;
import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class StadiumSearch 
implements SearchStrategy{
	
	private static final Logger log = LoggerFactory.getLogger(FacilitySearch.class);
	private StadiumMapper stadiumMapper;
	
	
	public StadiumSearch(StadiumMapper stadiumMapper) {
		this.stadiumMapper = stadiumMapper;
		
	}

	@Override
	public Map<String, Object> search(Map<String, Object> stadiumMap, int currentPage) {
		/*회원이 구장 전체 조회*/
		log.info("stadium 검색 정상 작동");
		Map<String, Object> resultMap = new HashMap<>();
		
		int rowPerPage = 12;
		int startPageNum = 1;
		int endPageNum = 10;

		double rowCount = stadiumMapper.getStadiumCount();

		int lastPage = (int) Math.ceil(rowCount / rowPerPage);

		int startRow = (currentPage - 1) * rowPerPage;



		if (lastPage > 10) {
			if (currentPage >= 6) {
				startPageNum = currentPage - 4;
				endPageNum = currentPage + 5;

				if (endPageNum >= lastPage) {
					startPageNum = lastPage - 9;
					endPageNum = lastPage;
				}
			}
		} else {
			endPageNum = lastPage;
		}

		stadiumMap.put("startRow", startRow);
		stadiumMap.put("rowPerPage", rowPerPage);
		log.info("stadiumMap : {}", stadiumMap);

		List<Stadium> searchList = stadiumMapper.getStadiumList(stadiumMap);

	
		resultMap.put("startRow", startRow);
		resultMap.put("rowPerPage", rowPerPage);
		resultMap.put("lastPage", 			lastPage);
		resultMap.put("startPageNum",		startPageNum);
		resultMap.put("endPageNum",			endPageNum);
		resultMap.put("searchList", searchList);
		resultMap.put("path", "stadium/stadiumList");
		return resultMap;
	}	
		
		

	@Override
	public SearchStrategyName getSearchStrategyName() {
		// TODO Auto-generated method stub
		return SearchStrategyName.StadiumSearch;
	}

}
