package ks43team03.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ks43team03.dto.Area;
import ks43team03.dto.Board;
import ks43team03.dto.Facility;
import ks43team03.dto.FacilityUser;
import ks43team03.dto.Lesson;
import ks43team03.dto.Review;
import ks43team03.dto.Stadium;
import ks43team03.service.BoardService;
import ks43team03.service.CommonService;
import ks43team03.service.FacilityService;
import ks43team03.service.SearchService;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Controller
@RequestMapping("/facility")
public class FacilityController {
	private static final Logger log = LoggerFactory.getLogger(FacilityController.class);

	private final FacilityService facilityService;
	private final BoardService boardService;
	private final CommonService commonService;
	private final SearchService searchService;

	public FacilityController(FacilityService facilityService
							, BoardService boardService
							,CommonService commonService
							,SearchService searchService) {
		this.facilityService = facilityService;
		this.boardService = boardService;
		this.commonService = commonService;
		this.searchService = searchService;
	}
	
	/*시설 가입 중복 체크*/
	@PostMapping("/userCheck")
	@ResponseBody
	public boolean isUserCheck(@RequestParam(value = "userId") String userId
							 , @RequestParam(value = "facilityCd") String facilityCd) {
		boolean userCheck = false;
		
		boolean result = facilityService.isUserCheck(userId, facilityCd);
		if(result) userCheck = true;
		
		return userCheck;
	}
	
	
	/*시설에 회원 가입*/
	@PostMapping("/addFacilityUser")
	public String addFacilityUser(FacilityUser facilityUser) {
	 facilityService.addFacilityUser(facilityUser);
		
		return "redirect:/facility/facilityDetail?"+"facilityCd=" +facilityUser.getFacilityCd();
	}
	
	/*시설에 회원가입*/
	@GetMapping("/addFacilityUser")
	public String addFacilityUser() {
		
		return"facility/facilityDetail";
	}
	
	
	/* 시설 상세 조회*/

	 @GetMapping("/facilityDetail")
	 public String getFacilityDetail(Model model
									 ,@RequestParam(name="facilityCd", required = false) String facilityCd){
										
			Facility facilityDetail = facilityService.getFacilityDetail(facilityCd);
			log.info(facilityCd);
			List<Stadium> stadiumList = facilityService.getStadiumListByCd(facilityCd);
			List<Lesson> lessonList = facilityService.getLessonList(facilityCd);
			List<Review> reviewList = facilityService.getReviewList(facilityCd);
			int reviewCount = facilityService.getReviewCountByCd(facilityCd);
			
			log.info("구장목록  : {}", stadiumList);
			
			model.addAttribute("title", "시설 상세 정보");
			model.addAttribute("facilityDetail", facilityDetail);
			model.addAttribute("stadiumList", stadiumList);
			model.addAttribute("lessonList", lessonList);
			model.addAttribute("reviewList", reviewList);
			model.addAttribute("reviewCount", reviewCount);
			
			List<Board> boardListByFacility = boardService.getBoardListByFacility(facilityCd);
			
			log.info("BoardListByFacility : {}", boardListByFacility);
			
			model.addAttribute("BoardListByFacility", boardListByFacility);
			
			return "facility/facilityDetail";
			
	 	}


	/* 사용자가 시설 조회 */
	@GetMapping("/facilityList")
	public String getFacilityList(Model model
								 ,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
								 ,@RequestParam(name="searchCtg", required = false) String searchCtg){
		
		Map<String, Object> searchMap = new HashMap<>();
		SearchStrategyName searchName = SearchStrategyName.valueOf(searchCtg);
	
		List<Area> areaList = commonService.getAreaList();
		
		searchMap.put("searchCtg", searchCtg);
	
		Map<String, Object> resultMap = searchService.findSearch(searchName, searchMap, currentPage);
		log.info("resultMap : {}",resultMap);

		model.addAttribute("searchList",			resultMap.get("searchList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("areaList",				areaList);
		model.addAttribute("title", "시설");
		return "facility/facilityList";
	}

}
