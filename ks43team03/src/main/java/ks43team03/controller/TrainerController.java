package ks43team03.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ks43team03.dto.TrainerCareer;
import ks43team03.dto.TrainerLicense;
import ks43team03.dto.TrainerProfile;
import ks43team03.service.SearchService;
import ks43team03.service.TrainerService;
import ks43team03.strategy.enumeration.SearchStrategyName;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
	
	private final TrainerService trainerService;
	private final SearchService searchService;
	
	public TrainerController(TrainerService trainerService
							,SearchService searchService) {
		this.trainerService = trainerService;
		this.searchService = searchService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(TrainerController.class);
	
	//자격증 수정 페이지 이동
	@GetMapping("/modifyLicense")
	public String modifyLicense(Model model
							   ,@RequestParam(name = "trainerLicenseCd") String trainerLicenseCd) {
		
		TrainerProfile trainerLicense = trainerService.getTrainerLicenseByTrainerLicenseCd(trainerLicenseCd);
		log.info("trainerLicense : {}", trainerLicense);
		
		model.addAttribute("trainerLicense",	trainerLicense);		
		model.addAttribute("title",				"자격증 수정");
		
		return "trainer/modifyLicense";
	}
	
	//경력 수정 페이지 이동
	@GetMapping("/modifyCareer")
	public String modifyCareer(Model model
							  ,@RequestParam(name = "trainerCareerCd") String trainerCareerCd) {
		
		TrainerProfile trainerCareer = trainerService.getTrainerCareerByTrainerCareerCd(trainerCareerCd);
		log.info("trainerCareer : {}", trainerCareer);
		
		model.addAttribute("trainerCareer",	trainerCareer);	
		model.addAttribute("title",			"경력 수정");
		
		return "trainer/modifyCareer";
	}
	
	//트레이너 정보 수정
	@PostMapping("/modifyTrainer")
	public String modifyTrainer(TrainerProfile trainerProfile
							   ,HttpSession session) {
		
		String userId = (String)session.getAttribute("SID");
		trainerProfile.setUserId(userId);
		
		log.info("trainerProfile : {}", trainerProfile);
		trainerService.modifyTrainerProfile(trainerProfile);
		
		return "redirect:/trainer/modifyTrainer";
	}
	
	//트레이너 정보 수정 페이지 이동
	@GetMapping("/modifyTrainer")
	public String modifyTrainer(Model model
							   ,HttpSession session) {
		
		String userId = (String)session.getAttribute("SID");
		
		log.info("트레이너 정보조회 코드 : {}", userId);
		if(userId != null && !userId.isEmpty()) {
			
			TrainerProfile trainerProfile = trainerService.getTrainerProfileByUserId(userId);
			
			model.addAttribute("trainerProfile",	trainerProfile);
		}
		
		model.addAttribute("title",				"트레이너 정보 수정");
		
		return "trainer/modifyTrainer";
	}
	
	//프로필 조회 페이지 이동
	@GetMapping("/trainerDetail")
	public String getTrainerDetail(Model model
								  ,HttpSession session
								  ,@RequestParam (value = "trainerCd", required = false)String trainerCd) {
		
		
		log.info("트레이너 정보조회 코드 : {}", trainerCd);
		String userId = (String)session.getAttribute("SID");
		
		
		Map<String, Object> trainerMap = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		model.addAttribute("trainerProfile",	null);
		
		if(trainerCd == null || trainerCd.isEmpty()) {
			if(userId != null && !userId.isEmpty()) {
				paramMap.put("userId", userId);
				
				trainerMap = trainerService.getTrainerInfoByTrainerCd(paramMap);
				
				log.info("트레이너 정보 : {}",		trainerMap.get("trainerProfile"));
				log.info("트레이너 경력 정보 : {}",	trainerMap.get("trainerCareer"));
				log.info("트레이너 자격증 정보 : {}",	trainerMap.get("trainerLicense"));
				
				model.addAttribute("trainerProfile",	trainerMap.get("trainerProfile"));
				model.addAttribute("trainerCareer",		trainerMap.get("trainerCareer"));
				model.addAttribute("trainerLicense",	trainerMap.get("trainerLicense"));
			}
		}else {
			paramMap.put("trainerCd", trainerCd);
			
			trainerMap = trainerService.getTrainerInfoByTrainerCd(paramMap);
			
			log.info("트레이너 정보 : {}",		trainerMap.get("trainerProfile"));
			log.info("트레이너 경력 정보 : {}",	trainerMap.get("trainerCareer"));
			log.info("트레이너 자격증 정보 : {}",	trainerMap.get("trainerLicense"));
			
			model.addAttribute("trainerProfile",	trainerMap.get("trainerProfile"));
			model.addAttribute("trainerCareer",		trainerMap.get("trainerCareer"));
			model.addAttribute("trainerLicense",	trainerMap.get("trainerLicense"));
		}
		
		
		model.addAttribute("title",				"트레이너 정보");
		
		return "trainer/trainerDetail";
	}
	
	//트레이너 리스트 페이지 이동
	@GetMapping("/trainerList")
	public String getTrainerList(Model model
								,@RequestParam(name = "searchCtg", required = false) String searchCtg
								,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		Map<String, Object> searchMap	  = new HashMap<>();
		SearchStrategyName searchName = SearchStrategyName.valueOf(searchCtg);
		
		searchMap.put("searchCtg", searchCtg);
		Map<String, Object> resultMap = searchService.findSearch(searchName, searchMap, currentPage);
		
		model.addAttribute("searchList"			, resultMap.get("searchList"));
		model.addAttribute("lastPage"			, resultMap.get("lastPage"));
		model.addAttribute("startPageNum"		, resultMap.get("startPageNum"));
		model.addAttribute("endPageNum"			, resultMap.get("endPageNum"));
		model.addAttribute("currentPage"		, currentPage);
		model.addAttribute("title"				, "트레이너 리스트");
		
		return "trainer/trainerList";
	}
	
	// 닉네임 중복체크 여부
	@PostMapping("/nicknameCheck")
	@ResponseBody
	public boolean isNickCheck(@RequestParam(value = "trainerNickname") String trainerNickname) {
		boolean nicknameCheck = false;
		log.info("닉네임중복체크 클릭시 요청받은 userId의 값: {}", trainerNickname);
		
		boolean result = trainerService.isNicknameCheck(trainerNickname);
		if(result) nicknameCheck = true;
		
		log.info("닉네임중복체크 여부 : {}", result);
		
		return nicknameCheck;
	}
	
	//자격증 등록
	@ResponseBody
	@PostMapping("/addLicense")
	public boolean addLicense(TrainerLicense trainerLicense
							 ,HttpSession session
							 ,HttpServletRequest request) {
		
		boolean addLicenseCheck = false;
		
		String userId = (String)session.getAttribute("SID");
		List<TrainerLicense> trainerLicenseList = trainerLicense.getTrainerLicenseList();
		log.info("trainerLicenseList : {}",trainerLicenseList);
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			System.out.println(System.getProperty("user.dir"));
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		int result = trainerService.addTrainerLicense(trainerLicenseList, userId, fileRealPath);
		
		if(result>0) addLicenseCheck = true;
		
		return addLicenseCheck;
	}
	
	//자격증 등록 페이지 이동
	@GetMapping("/addLicense")
	public String addLicense(Model model
							,@RequestParam(value = "trainerCd") String trainerCd) {
		
		model.addAttribute("trainerCd", trainerCd);
		model.addAttribute("title", 	"자격증 등록");
		
		return "trainer/addLicense";
	}
	
	//경력 등록
	@ResponseBody
	@PostMapping("/addCareer")
	public boolean addCareer(TrainerCareer trainerCareer
							,HttpSession session
							,HttpServletRequest request) {
		
		boolean addCareerCheck = false;
		
		String userId = (String)session.getAttribute("SID");
		
		List<TrainerCareer> trainerCareerList = trainerCareer.getTrainerCareerList();
		log.info("trainerCareerList : {}", trainerCareerList);
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			log.info("user.dir : {}", System.getProperty("user.dir"));
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		int result = trainerService.addTrainerCareer(trainerCareerList, userId, fileRealPath);
		
		if(result > 0) addCareerCheck = true;
		
		return addCareerCheck;
	}
	
	//경력 등록 페이지 이동
	@GetMapping("/addCareer")
	public String addCareer(Model model
						   ,@RequestParam(value = "trainerCd") String trainerCd) {
		
		model.addAttribute("trainerCd", trainerCd);
		model.addAttribute("title", 	"경력 등록");
		
		return "trainer/addCareer";
	}
	
	//트레이너 등록
	@PostMapping("/addTrainer")
	public String addTrainer(TrainerProfile trainerProfile
							,RedirectAttributes reAttr
							,@RequestParam MultipartFile[] trainerImgFile
							,HttpServletRequest request) {
		
		log.info("trainerProfile : {}",trainerProfile);
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			System.out.println(System.getProperty("user.dir"));
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		      
		String trainerCd = trainerService.addtrainer(trainerProfile, trainerImgFile, fileRealPath);
		log.info("trainerCd : {}",trainerCd);
		
		reAttr.addAttribute("trainerCd", trainerCd);
		
		return "redirect:/trainer/addCareer";
	}	
	
	//트레이너 등록 페이지 이동
	@GetMapping("/addTrainer")
	public String addTrainer(Model model) {
		
		model.addAttribute("title", 	"트레이너 등록");
		
		return "trainer/addTrainer";
	}
}
