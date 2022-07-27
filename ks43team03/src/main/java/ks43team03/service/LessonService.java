package ks43team03.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import ks43team03.common.FileUtils;
import ks43team03.dto.Lesson;
import ks43team03.mapper.CommonMapper;
import ks43team03.mapper.FacilityGoodsMapper;
import ks43team03.mapper.FileMapper;
import ks43team03.mapper.LessonMapper;

@Service
@Transactional
public class LessonService {
	
	private static final Logger log = LoggerFactory.getLogger(LessonService.class);
	private final LessonMapper lessonMapper;
	private final FileMapper   fileMapper;
	private final FacilityGoodsMapper facilityGoodsMapper;
	private final CommonMapper commonMapper;
	
	public LessonService(LessonMapper lessonMapper, FileMapper fileMapper, FacilityGoodsMapper facilityGoodsMapper, CommonMapper commonMapper) {
		this.lessonMapper = lessonMapper;
		this.fileMapper	  = fileMapper;
		this.facilityGoodsMapper = facilityGoodsMapper;
		this.commonMapper		 = commonMapper;
	}
	
	// 레슨 리스트 가져오기
	public List<Lesson> getfacilityLessonList(String facilityCd) {
		List<Lesson> lessonList = lessonMapper.getFacilityLessonList(facilityCd);
		log.info(lessonList + "!!!");
		return lessonList;
	}

	//레슨 등록하기
	public void addLesson(Lesson lesson, MultipartFile[] uploadfile, String fileRealPath) {
		
		log.info(uploadfile.toString());
		
		boolean fileCheck = true;
		
		for (MultipartFile multipartFile : uploadfile){
			if(!multipartFile.isEmpty()) {
				fileCheck = false;
			}
		}
		//파일이 널이 아니라면
		if (!fileCheck) {
			log.info("파일 있음!!" + uploadfile + "파일있음!!");
			String uproaderId 		= lesson.getUserId();
			String facilityGoodsCd;
			
			facilityGoodsMapper.addFacilityGoods(lesson.getFacilityGoods());
			facilityGoodsCd = lesson.getFacilityGoods().getFacilityGoodsCd();
			
			
			
			FileUtils fu = new FileUtils(uploadfile, uproaderId, fileRealPath);
			List<Map<String, String>> dtoFileList = fu.parseFileInfo();
		
			// 1. lesson 테이블에 삽입
			//lessonMapper.addLesson(lesson);
			log.info(lesson + "LessonService/addLesson/lesson");

			lessonMapper.addLesson(lesson);
			
			
			log.info(dtoFileList + "LessonService/addLesson");
			if(!(dtoFileList == null)) {
				// 2. t_file 테이블에 삽입
				fileMapper.uploadFile(dtoFileList);
			
				// 3. 릴레이션 테이블에 삽입
				List<Map<String, String>> relationFileList = new ArrayList<>();
				for(Map<String, String> m : dtoFileList) {
					m.put("facilityGoodsCd", facilityGoodsCd);
					relationFileList.add(m);
				}
				log.info("relationFileList", relationFileList);
				
				fileMapper.uploadRelationFile(relationFileList);
			}
			
			
			
			/****************
			 *				*
			 * 알림 테이블 삽입	*
			 * 				*
			 ****************/
			
			//List<Map<String, String>> notification = new ArrayList<>();
			
	    } else {
			facilityGoodsMapper.addFacilityGoods(lesson.getFacilityGoods());
			log.info(lesson + "LessonService/addLesson/lesson");
			lessonMapper.addLesson(lesson);
	    }
	}

	public Lesson getLessonInfoByCd(String lessonCd) {
		
		Lesson lesson = lessonMapper.getLessonInfoByCd(lessonCd);
		log.info(lesson + "lessonService/getLessonInfoById");
		return lesson;
	}


	public int modifyLesson(Map<String, Object> paramMap, MultipartFile[] uploadfile) {
		log.info("___________________________________________________");
		log.info("_______________start modifyLesson__________________");
		/**
		 * 조인 해서 업데이트, 대표 이미지만 컬럼 Y로 바뀌게 할 것 
		 **/
		int result = 0;
		
		
		Lesson lesson			   = (Lesson)paramMap.get("lesson");
		String fileRealPath		   = (String)paramMap.get("fileRealPath");
		String[] fileCdArray	   = (String[])paramMap.get("fileCd");
		String[] representImgArray = (String[])paramMap.get("representImg");
		String[] deleteImg 		   = (String[])paramMap.get("deleteImg");
		
		boolean fileCheck = true;
		
		for (MultipartFile multipartFile : uploadfile){
			if(!multipartFile.isEmpty()) {
				fileCheck = false;
			}
		}
		//파일이 널이 아니라면
		if (!fileCheck) {
			log.info("파일 있음!!" + uploadfile + "파일있음!!");
			String uproaderId 		= lesson.getUserId();
			String facilityGoodsCd;
			
			facilityGoodsCd = lesson.getFacilityGoodsCd();
			
			FileUtils fu = new FileUtils(uploadfile, uproaderId, fileRealPath);
			List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			
			dtoFileList.get(0).remove("representImg");
			dtoFileList.get(0).put("representImg", "N");
			
			log.info(dtoFileList + "LessonService/addLesson");
			if(!(dtoFileList == null)) {
				
				fileMapper.uploadFile(dtoFileList);
			
				// 3. 릴레이션 테이블에 삽입
				List<Map<String, String>> relationFileList = new ArrayList<>();
				for(Map<String, String> m : dtoFileList) {
					m.put("facilityGoodsCd", facilityGoodsCd);
					relationFileList.add(m);
				}
				
				log.info("relationFileList", relationFileList);
				
				fileMapper.uploadRelationFile(relationFileList);
			}
			
			
			
			/****************
			 *				*
			 * 알림 테이블 삽입	*
			 * 				*
			 ****************/
			
			//List<Map<String, String>> notification = new ArrayList<>();
			
	    } 
	  
		log.info("lesson : {}", paramMap);
		
		for(int i = 0; i < fileCdArray.length; i++) {
			Map<String, String> fileMap = new HashMap<>();
			fileMap.put("fileCd", fileCdArray[i]);
			fileMap.put("representImg", representImgArray[i]);
			fileMap.put("facilityGoodsCd", lesson.getFacilityGoodsCd());
			fileMapper.modifyFile(fileMap);
		}
		if(!ObjectUtils.isEmpty(deleteImg)) {
			for(int j = 0; j < deleteImg.length; j++) {
				fileMapper.deleteRelFileWithGoods(deleteImg[j]);
				fileMapper.deleteFile(deleteImg[j]);
			}
		}
		
		result = lessonMapper.modifyLesson(lesson);
		
		log.info("_______________end   modifyLesson");
		log.info("___________________________________________________");
		
	    
		return result;
	}

}
