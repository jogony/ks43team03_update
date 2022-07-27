package ks43team03.strategy.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ks43team03.dto.Lesson;
import ks43team03.mapper.LessonMapper;
import ks43team03.strategy.SearchStrategy;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Component
public class LessonSearch 
implements SearchStrategy{
	
	private static final Logger log = LoggerFactory.getLogger(LessonSearch.class);
	private LessonMapper lessonMapper;
	
	public LessonSearch(LessonMapper lessonMapper) {
		this.lessonMapper = lessonMapper;
	}
	
	@Override
	public Map<String, Object> search(Map<String, Object> lessonMap, int currentPage) {
		log.info("___________________________________________________");
		log.info("_______________start LessonSearch__________________");
		log.info("lessonMap : {}",lessonMap);

		
		
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int rowPerPage = 9;
	
		double rowCount = lessonMapper.getLessonCount();
	
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
		
		resultMap.put("startRow", startRow);
		resultMap.put("rowPerPage", rowPerPage);
		resultMap.put("lastPage", 		lastPage);
		resultMap.put("startPageNum",	startPageNum);
		resultMap.put("endPageNum",		endPageNum);
		
		lessonMap.put("startRow", startRow);
		lessonMap.put("rowPerPage", rowPerPage);
		log.info("lessonMap : {}", lessonMap);
		List<Lesson> searchList = lessonMapper.getLessonListForUser(lessonMap);
	
		resultMap.put("searchList", searchList);
		resultMap.put("title", "레슨목록");
		resultMap.put("path", "lesson/lessonListUser");
		log.info("_______________end   LessonSearch__________________");
		log.info("___________________________________________________");
		
		return resultMap;
	}

	@Override
	public SearchStrategyName getSearchStrategyName() {
		// TODO Auto-generated method stub
		return SearchStrategyName.LessonSearch;
	}
}
