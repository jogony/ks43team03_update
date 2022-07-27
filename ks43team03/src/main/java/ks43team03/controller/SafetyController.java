package ks43team03.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import org.springframework.web.multipart.MultipartFile;

import ks43team03.dto.Safety;
import ks43team03.service.SafetyService;

@Controller
@RequestMapping("/safety")
public class SafetyController {
	private static final Logger log = LoggerFactory.getLogger(SafetyController.class);
	private final SafetyService safetyService;
	
	public SafetyController(SafetyService safetyService) {
		this.safetyService = safetyService;
	}

	//안전점검 등록
	  @PostMapping("/addSafety") 
	  public String addSafety(Safety safety
			  					,@RequestParam(name="safetyCheckCd", required = false) String safetyCheckCd
			  					,@RequestParam MultipartFile[] safetyFile, Model model
			  					,HttpServletRequest request
			  					,HttpSession session) {
		  log.info("안전점검 등록화면에서 입력한 data : {}", safety);
		  
		  String serverName = request.getServerName();
			String fileRealPath = "";
			if("localhost".equals(serverName)) {				
				fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
				//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
			}else {
				fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
			}
			
			safetyService.addSafety(safety, safetyFile, fileRealPath, session.getAttribute("SID"));
			
		  return "redirect:/safety/addSafety"; 
	  }	
	
	// 안전점검 등록 페이지 이동
	@GetMapping("/addSafety")
	public String addSafety(Model model) {
		
		model.addAttribute("title", "안전점검 등록");
		
		return "safety/addSafety";
	}
	
	// 안전점검 등록 시설 목록 조회(전체)
	@GetMapping("/safetyList")
	public String getSafetyList(@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
			  ,Model model) {

		Map<String, Object> resultMap = safetyService.getSafetyList(currentPage);
		resultMap.get("lastPage");
		
		model.addAttribute("title", "안전점검 등록 시설 조회");
		model.addAttribute("resultMap", 		resultMap);
		model.addAttribute("currentPage", 		currentPage);
		model.addAttribute("safetyList", 		resultMap.get("safetyList"));
		model.addAttribute("lastPage", 			resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 		resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 		resultMap.get("endPageNum"));
		System.out.println(model + "model!!!!!!!!!!");
		return "safety/safetyList";
	}	
	
	//시설 관리자의 안전점검 등록 목록 조회
	@GetMapping("/safetyListById")
	public String getsafetyListById(Model model, HttpSession session) {
		
		String sessionId = (String) session.getAttribute("SID");
		
		log.info("회원 아이디 : {}", sessionId);
		
		if (Objects.isNull(sessionId)) {

			return "safety/safetyListById";
			
		}		
		
		List<Safety> safetyListById = safetyService.getSafetyListById(sessionId);
		
		log.info("safetyListById 조회 : {}", safetyListById);
		
		model.addAttribute("title", "내 안전점검 등록 시설 조회");
		model.addAttribute("safetyListById", safetyListById);
		model.addAttribute("sessionId", sessionId);
		
		return "safety/safetyListById";
	}
	
	
	
	//안전점검 등록 정보 수정 처리
	@PostMapping("/modifySafety")
	public String modifySafety(Safety safety, Model model) {
		log.info("안전점검 등록 정보 수정 폼 입력값 : {}", safety);
		
		safetyService.modifySafety(safety);
		
		return "redirect:/safety/safetyListById";
		
	}
	
	//안전점검 등록 정보 수정 
	@GetMapping("/modifySafety")
	public String modifySafety(@RequestParam(value = "userId" , required = false)String userId,
							   @RequestParam(value="safetyCheckCd", required=false) String safetyCheckCd,
							   Model model) {
		
		log.info("userId : {}", userId);
		log.info("safetyCheckCd : {}", safetyCheckCd);
		
		Safety safetyInfoByCd = safetyService.getSafetyInfoByCd(safetyCheckCd);
		
		model.addAttribute("title", "안전점검 등록 정보 수정");
		model.addAttribute("safetyInfoByCd", safetyInfoByCd);
		
		return "safety/modifySafety";		
		
	}
	
	//안전점검 등록 정보 삭제
	@GetMapping("/removeSafety")
	public String removeSafety (Model model, Safety safety) {
		
		log.info("안전점검 등록 정보 삭제 :::: {}", safety);
		
		safetyService.removeSafety(safety);
		
		return "redirect:/safety/safetyList";
	}

	//시설 관리자의 안전점검 등록 정보 삭제
	@GetMapping("/removeSafety2")
	public String removeSafety2 (Model model, Safety safety) {
		
		log.info("시설 관리자의 안전점검 등록 정보 삭제 :::: {}", safety);
		
		safetyService.removeSafety2(safety);
		
		return "redirect:/safety/safetyListById";
	}	
	

	// 관리자의 안전점검 결과 목록 조회(전체) 
	@GetMapping("/safetyResultList")
	public String getSafetyResultList(@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
			  ,Model model) {

		Map<String, Object> resultMap = safetyService.getSafetyResultList(currentPage);
		resultMap.get("lastPage");
		
		model.addAttribute("title", "안전점검 결과 목록 조회");
		model.addAttribute("resultMap", 		resultMap);
		model.addAttribute("currentPage", 		currentPage);
		model.addAttribute("safetyResultList", 	resultMap.get("safetyResultList"));
		model.addAttribute("lastPage", 			resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 		resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 		resultMap.get("endPageNum"));
		System.out.println(model + "model!!!!!!!!!!");
		return "safety/safetyResultList";
	}	
	
	//시설 관리자의 안전점검 결과 목록 조회 
	@GetMapping("/safetyResultListById")
	public String getSafetyResultListById(Model model, HttpSession session) {
		
		String sessionId = (String) session.getAttribute("SID");
		
		log.info("회원 아이디 : {}", sessionId);
		
		if (Objects.isNull(sessionId)) {

			return "safety/safetyResultListById";
			
		}		
		
		List<Safety> safetyResultListById = safetyService.getSafetyResultListById(sessionId);
		
		log.info("safetyResultListById 조회 : {}", safetyResultListById);
		
		model.addAttribute("title", "내 안전점검 등록 시설 조회");
		model.addAttribute("safetyResultListById", safetyResultListById);
		model.addAttribute("sessionId", sessionId);
		
		return "safety/safetyResultListById";
	}
	
	
}
	
/*
@PostMapping("/addSafety")
public String addSafety(Safety safety) {
	
	log.info("안전점검 등록 시작");
	
	log.info("안전점검 등록에서 입력받은 데이터:{}", safety);		
	
	int result = safetyService.addSafety(safety);
	
	return "redirect:/safety/addSafety";
}
*/
	
	