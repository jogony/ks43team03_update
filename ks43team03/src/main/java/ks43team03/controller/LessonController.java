package ks43team03.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ks43team03.dto.Area;
import ks43team03.dto.Facility;
import ks43team03.dto.FacilityGoods;
import ks43team03.dto.Lesson;
import ks43team03.dto.Order;
import ks43team03.service.AdminFacilityService;
import ks43team03.service.CommonService;
import ks43team03.service.LessonService;
import ks43team03.service.SearchService;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Controller
@RequestMapping("/lesson")
public class LessonController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private final LessonService 	   lessonService;
	private final CommonService 	   commonService;
	private final SearchService 	   searchService;
	private final AdminFacilityService adminFacilityService;
	
	public LessonController(LessonService lessonService, CommonService commonService, SearchService searchService, AdminFacilityService adminFacilityService) {
		this.lessonService 		  = lessonService;
		this.commonService 		  = commonService;
		this.searchService		  = searchService;
		this.adminFacilityService = adminFacilityService;
	}
	
	/**
	 *  레슨 체크
	 **/
	@ResponseBody
	@PostMapping("/lessonOrderCheck")
	public Boolean lessonOrderCheck(HttpSession session,
									@RequestBody Map<String, String> paramMap) {
		String userId = (String)session.getAttribute("SID");
		paramMap.put("userId", userId);
		
		Boolean result = false;
		
		Order order = commonService.goodsOrderCheck(paramMap);
		
		if(!ObjectUtils.isEmpty(order)) {
			result = true;
		}
		return result;
	}
	/**
	 *  레슨 예약
	 **/
	@GetMapping("/lessonReserve")
	public String lessonReservateion(@RequestParam(name		  = "lessonCd"
												   ,required 	  = false
												   ,defaultValue  = "error") String lessonCd
									 			   ,Model model) {
		
		Lesson lesson = lessonService.getLessonInfoByCd(lessonCd);
		
		model.addAttribute("lesson", lesson);
		model.addAttribute("title" , "레슨 예약");
		
		log.info("model : {}", model);
		
		return "lesson/lessonReservation";
	}
	/**
	 *  회원이 보는 레슨 리스트 
	 **/
	@GetMapping("/detailLessonForUser")
	public String detailLessonForUser(@RequestParam(name		  = "lessonCd"
												   ,required 	  = false
												   ,defaultValue  = "error") String lessonCd
									 ,Model model) {
		
		Lesson lesson = lessonService.getLessonInfoByCd(lessonCd);
		
		model.addAttribute("lesson", lesson);
		model.addAttribute("title" , "상품상세보기");
		
		log.info("model : {}", model);
		
		return "lesson/detailLessonUser";
	}
	
	@GetMapping("/lessonListForUser")
	public String lessonListforUser(Model model
									,@RequestParam(name="searchCtg", required = false) String searchCtg
									,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage){
		Map<String, Object> searchMap = new HashMap<>();
		SearchStrategyName searchName = SearchStrategyName.valueOf(searchCtg);
		
		
		searchMap.put("searchCtg", searchCtg);
		Map<String, Object> resultMap = searchService.findSearch(searchName, searchMap, currentPage);
		
		model.addAttribute("searchList"			, resultMap.get("searchList"));
		model.addAttribute("lastPage"			, resultMap.get("lastPage"));
		model.addAttribute("startPageNum"		, resultMap.get("startPageNum"));
		model.addAttribute("endPageNum"			, resultMap.get("endPageNum"));
		model.addAttribute("currentPage"		, currentPage);
		model.addAttribute("title", 			"레슨 목록");
		return "lesson/lessonListUser";
	};
	/**
	 * 레슨 상세 조회 
	 **/
	@GetMapping("/detailLesson")
	public String detailLesson(@RequestParam(name="lessonCd") String lessonCd
								,Model model) {
		
		Lesson lesson = lessonService.getLessonInfoByCd(lessonCd);
		model.addAttribute(lesson);
		model.addAttribute("title", "레슨상세조회");
		log.info("model : {}", model);
		return "lesson/detailLessonAdmin";
	}
	/**
	 * 레슨 수정
	 **/
	@PostMapping("/modifyLesson")
	public String modifyLesson(Lesson lesson
							   ,Model model
							   ,HttpSession session
							   ,@RequestParam(name="lessonImgFile", required = false) MultipartFile[] lessonImgFile
							   ,HttpServletRequest request
							   ,String[] fileCd
							   ,String[] representImg
							   ,String[] deleteImg) {
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		if("localhost".equals(serverName)) {				
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		String userId = (String)session.getAttribute("SID");
		log.info(userId);
		
		lesson.setUserId(userId);
		Map<String, Object> paramMap = new HashMap<>();
		
		System.out.println(lesson.getSportsCd());
		
		paramMap.put("fileCd", fileCd);
		paramMap.put("lesson", lesson);
		paramMap.put("representImg", representImg);
		paramMap.put("deleteImg", deleteImg);
		paramMap.put("fileRealPath", fileRealPath);
		
		lessonService.modifyLesson(paramMap, lessonImgFile);
		
		model.addAttribute("facilityCd", lesson.getFacilityCd());
		
		return "redirect:/lesson/facilityLessonList?" + "facilityCd="+ lesson.getFacilityCd();
	}
	
	@GetMapping("/modifyLesson")
	public String modifyLesson(Model model
								,@RequestParam(name="lessonCd") String lessonCd) {
		Lesson lesson = lessonService.getLessonInfoByCd(lessonCd);
		model.addAttribute("title", "레슨수정");
		model.addAttribute("lesson", lesson);
		return "lesson/modifyLessonAdmin";
	}
	/**
	 * 시설 내에 등록된 레슨 리스트
	 **/
	@GetMapping("/facilityLessonList")
	public String facilityLessonList(HttpSession session
									,Model model) {
		String sessionId = (String) session.getAttribute("SID");
		List<Facility> adminFacilityListById = adminFacilityService.getAdminFacilityListById(sessionId);
		log.info(adminFacilityListById + "{} : lessonController/facilityLesosnLilst");
		
		List<Map<String, Object>> resultMap = new ArrayList<>();
		for (Facility facility : adminFacilityListById) {
			Map<String, Object> midMap = new HashMap<>();
			List<Lesson> lessonList = lessonService.getfacilityLessonList(facility.getFacilityCd());
			midMap.put("lessonList", lessonList);
			midMap.put("facility", facility);
			
			resultMap.add(midMap);
		}
		log.info("resultMap : {}", resultMap);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("title", "레슨리스트");
		
		return "lesson/lessonList";
	}
	
	@PostMapping("/addLesson")
	public String addLesson(
			FacilityGoods facilityGoods,
			Lesson lesson,
			@RequestParam MultipartFile[] lessonImgFile,
			Model model, 
			HttpServletRequest request) {
		lesson.setFacilityGoods(facilityGoods);
		log.info("LessonController addLesson/facilityGoods : {}", facilityGoods);
		log.info("LessonController addLesson/lesson : {}", lesson);
		log.info("lesson : {}", lesson);
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		if("localhost".equals(serverName)) {				
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		lessonService.addLesson(lesson, lessonImgFile, fileRealPath);
		
		
		return "redirect:/lesson/facilityLessonList?" + "facilityCd="+ lesson.getFacilityCd();

		
	}

}
