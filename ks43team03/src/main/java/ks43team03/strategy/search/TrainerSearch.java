package ks43team03.strategy.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.TrainerProfile;
import ks43team03.mapper.TrainerMapper;
import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class TrainerSearch 
implements SearchStrategy{
		
	private static final Logger log = LoggerFactory.getLogger(LessonSearch.class);
	private TrainerMapper trainerMapper;
	
	public TrainerSearch(TrainerMapper trainerMapper) {
		this.trainerMapper = trainerMapper;
	}
	
	@Override
	public Map<String, Object> search(Map<String, Object> searchMap, int currentPage) {
		log.info("_______________start TrainerSearch__________________");
		log.info("searchMap : {}", searchMap);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int rowPerPage = 12;
	
		double rowCount = trainerMapper.getTrainerCount();
	
		int lastPage = (int)Math.ceil(rowCount/rowPerPage);
		

		int startRow = (currentPage - 1)*rowPerPage;
		
		int startPageNum = 1;
		int endPageNum = 10;
		
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
		
		resultMap.put("startRow",		startRow);
		resultMap.put("rowPerPage",		rowPerPage);
		resultMap.put("lastPage", 		lastPage);
		resultMap.put("startPageNum",	startPageNum);
		resultMap.put("endPageNum",		endPageNum);
		
		searchMap.put("startRow", 		startRow);
		searchMap.put("rowPerPage", 	rowPerPage);
		
		log.info("searchMap : {}", searchMap);
		
		List<TrainerProfile> searchList = trainerMapper.getTrainerList(searchMap);
	
		resultMap.put("searchList", 	searchList);
		resultMap.put("title",			"트레이너 목록");
		resultMap.put("path", 			"trainer/trainerList");
		log.info("_______________end   TrainerSearch__________________");
		
		return resultMap;
	}

	@Override
	public SearchStrategyName getSearchStrategyName() {
		// TODO Auto-generated method stub
		return SearchStrategyName.TrainerSearch;
	}
}
