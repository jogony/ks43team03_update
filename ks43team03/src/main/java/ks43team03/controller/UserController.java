package ks43team03.controller;

import java.util.ArrayList;
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
import ks43team03.dto.User;
import ks43team03.service.AdminFacilityService;
import ks43team03.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private final UserService userService; 
	private final AdminFacilityService adminFacilityService; 
	
	public UserController(UserService userService, AdminFacilityService adminFacilityService) {
		this.userService			=	userService;
		this.adminFacilityService	=	adminFacilityService;
	}
	
	//시설 내 회원 승인
	@GetMapping("/modifyFacilityUserState")
	public String modifyFacilityUserState(@RequestParam(name = "facilityApproveState")String facilityApproveState
										 ,@RequestParam(name = "userId")String userId
										 ,@RequestParam(name = "facilityCd")String facilityCd) {
		
		userService.modifyFacilityUserState(facilityApproveState, userId, facilityCd);
		
		return "redirect:/user/facilityUser";
	}
	
	//시설 내 회원 목록 조회
	@GetMapping("/facilityUser")
	public String getFacilityUserList(Model model
									 ,HttpSession session) {
		
		String sessionId = (String)session.getAttribute("SID");
		log.info("시설조회 아이디 : {}", sessionId);
		
		if(sessionId != null && !sessionId.isEmpty()) {
			
			//아이디로 시설 검색
			List<Facility> adminFacilityListById = adminFacilityService.getAdminFacilityListById(sessionId);
			log.info("adminFacilityListById:{}",adminFacilityListById);
			
			//시설에 가입된 회원 조회
			List<Map<String, Object>> facilityUserList = userService.getFacilityUserList(adminFacilityListById);
			model.addAttribute("facilityUserList",	facilityUserList);
		}
		
		model.addAttribute("title", 			"시설 내 회원 목록");
		
		return "user/facilityUser";
	}
	
	//회원 삭제 변경
	@PostMapping("/removeUser")
	public String removeUser(Model model
							,@RequestParam(name="userPw") String userPw
							,HttpSession session) {
		String sessionId = (String)session.getAttribute("SID"); 
		
		log.info("회원아이디 : {}", sessionId);
		log.info("회원비밀번호 : {}", userPw);
		
		User user = userService.getUserInfoById(sessionId);
		
		log.info("DB회원 : {}", user);
		
		if(userPw.equals(user.getUserPw())) {
			user.setUserDrop("Y");
			userService.modifyUser(user);
			session.invalidate();
		}
		return "redirect:/";
	}
	
	//회원 삭제 시 비밀번호 확인
	@PostMapping("/pwCheck")
	@ResponseBody
	public boolean isPwCheck(@RequestParam(value = "userId") String userId
							,@RequestParam(value = "userPw") String userPw) {
		
		boolean pwCheck = false;
		
		log.info("패스워드체크 클릭시 요청받은 userId의 값: {}", userId);
		log.info("패스워드체크 클릭시 요청받은 userPw의 값: {}", userPw);
		
		User userCheck = userService.getUserInfoById(userId);
		
		if(userPw.equals(userCheck.getUserPw())) {
			
			pwCheck = true;
			
		}
		
		return pwCheck;
	}
	
	//회원 비밀번호 변경
	@PostMapping("/modifyUserPw")
	public String modifyUserPw(User user
							  ,Model model
							  ,@RequestParam(name="nowUserPwCheck") String nowUserPwCheck) {
		
		log.info("비밀번호 변경 회원정보:{}", user);
		
		User userCheck = userService.getUserInfoById(user.getUserId());
		
		if(nowUserPwCheck.equals(userCheck.getUserPw())) {
			userService.modifyUser(user);
			return "redirect:/user/userDetail";
			
		}else {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("result", "비밀번호가 일치하지 않습니다.");
			
			return "user/modifyUserPw";
		}
		
	}
	
	//회원 비밀번호 변경 페이지 이동
	@GetMapping("/modifyUserPw")
	public String modifyUserPw(Model model
							  ,@RequestParam(name="userId",required = false) String userId) {
		
		User user = userService.getUserInfoById(userId);
		
		log.info("회원 비밀번호 변경 아이디 : {}", userId);
		
		model.addAttribute("title", "비밀번호 변경");
		model.addAttribute("user", user);
		
		return "user/modifyUserPw";
	}
	
	//회원 정보 수정
	@PostMapping("/modifyUser")
	public String modifyUser(User user
							,Model model) {
		
		log.info("회원수정정보:{}", user);
		
		userService.modifyUser(user);
		
		return "redirect:/user/userDetail";
	}
	
	//회원 정보 조회
	@GetMapping("/userDetail")
	public String getUserDetail(Model model
							   ,HttpSession session) {
		
		String sessionId = (String)session.getAttribute("SID");
		
		log.info("회원정보조회 아이디 : {}", sessionId);
		
		model.addAttribute("title", "회원정보");
		
		User user = userService.getUserInfoById(sessionId);
		
		log.info("회원정보조회 : {}", user);
		
		model.addAttribute("user", user);
		model.addAttribute("title", "회원 정보");
		return "user/userDetail";
	}
	
	//회원 가입
	@PostMapping("/addUser")
	public String addUser(User user) {
		
		log.info("회원가입폼 시작");
		
		log.info("회원가입폼에서 입력받은 데이터:{}", user);
		
		int result = userService.addUser(user);
		
		log.info("result : {}", result);
		
		return "redirect:/";
	}
	
	//회원 가입 페이지 이동
	@GetMapping("/addUser")
	public String addUser(Model model) {
		
		model.addAttribute("title", "회원가입");
		
		return "user/addUser";
	}
	
	// 아이디 중복체크 여부
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean isIdCheck(@RequestParam(value = "userId") String userId) {
		boolean idCheck = false;
		log.info("아이디중복체크 클릭시 요청받은 userId의 값: {}", userId);
		
		boolean result = userService.isIdCheck(userId);
		if(result) idCheck = true;
		
		log.info("아이디중복체크 여부 : {}", result);
		return idCheck;
	}
	
	//회원 전체 목록 조회
	@GetMapping("/userList")
	public String getUserList(Model model
							 ,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		Map<String, Object> resultMap = userService.getUserList(currentPage);
		
		log.info("resultMap : {}",resultMap);
		log.info("resultMap.get(\"userList\") : {}",resultMap.get("userList"));
		
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("userList",				resultMap.get("userList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("title", 				"회원 전체 목록");
		return "user/userList";
	}
}
