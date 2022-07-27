package ks43team03.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ks43team03.dto.Facility;
import ks43team03.dto.Review;
import ks43team03.service.FacilityService;
import ks43team03.service.ReviewService;


@Controller
@RequestMapping("/review")
public class ReviewController {
	
	private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
	
	private final ReviewService reviewService;
	private final FacilityService facilityService;

	
	public ReviewController(ReviewService reviewService, FacilityService facilityService) {
		this.reviewService = reviewService;
		this.facilityService = facilityService;
	}
	
	//후기 삭제
	@GetMapping("/reviewRemove")
	public String removeReview(Review review
							  ,HttpSession session
							  ,@RequestParam(name = "reviewCd", required = false) String reviewCd
							  ,@RequestParam(name = "facilityCd", required = false) String facilityCd) {
		String sessionId = (String) session.getAttribute("SID");
		Facility facility = facilityService.getFacilityDetail(facilityCd);
		log.info("sessionId : {}", sessionId);
		log.info("reviewCd : {}", reviewCd);
		reviewService.removeReview(reviewCd, sessionId);
		
		return"redirect:/facility/facilityDetail?"+"facilityCd=" +facility.getFacilityCd();
		
	}
	
	
	
	//결제한 회원만 리뷰작성 
	@PostMapping("/orderCheck")
	@ResponseBody
	public boolean isOrderCheck(@RequestParam(value = "userId") String userId
								,@RequestParam(value = "facilityCd") String facilityCd) {
		boolean orderCheck = true;
		
		boolean result = reviewService.isOrderCheck(userId, facilityCd);
		log.info("result :{} ", result);
		if(result) orderCheck = false;
		
		return orderCheck;
	}
	
	
	
	//시설에 후기등록처리
	@PostMapping("/addReview")
	public String addReview(Review review
			,@RequestParam(name = "facilityCd", required = false) String facilityCd) {
		reviewService.addReview(review);
		Facility facility = facilityService.getFacilityDetail(facilityCd);
		log.info("review : {}", review);
		log.info("facilityCd : {}", facilityCd);
		
		return "redirect:/facility/facilityDetail?"+"facilityCd=" +facility.getFacilityCd();
	}
	//시설에서 후기 등록 화면
	@GetMapping("/addReview")
	public String addReview() {
		
		return"facility/facilityDetail";
	}
	
	//시설관리자가 후기 조회
	@GetMapping("/adminReviewListById")
	public String getAdminReviewListById(Model model
										,HttpSession session) {
		String sessionId = (String) session.getAttribute("SID");
		log.info("후기 조회 아이디  : {}", sessionId);
		
		List<Review> adminReviewListById = reviewService.getAdminReviewListById(sessionId);
		model.addAttribute("title", "내 시설 후기");
		model.addAttribute("adminReviewListById", adminReviewListById);
		
		return "review/adminReviewListById";
		
	}
	
	//관리자가 후기조회
	@GetMapping("/adminReviewList")
	public String getAdminReviewList(Model model
								,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		Map<String, Object> resultMap = reviewService.getAdminReviewList(currentPage);
				
		log.info("resultMap : {}",resultMap);
		log.info("resultMap.get(\"adminReviewList\") : {}",resultMap.get("adminReviewList"));
		
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("adminReviewList",			resultMap.get("adminReviewList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("title", "후기 전체 조회");
		return "review/adminReviewList";
	}
	
}
