package ks43team03.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ks43team03.dto.User;
import ks43team03.service.UserService;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private final UserService userService;
	
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	//로그인 기록
	@GetMapping("/loginHistory")
	public String loginHistory(@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
							  ,Model model) {
		
		Map<String, Object> resultMap = userService.getLoginHistory(currentPage);
		resultMap.get("lastPage");
		
		model.addAttribute("title", "로그인 기록");
		model.addAttribute("resultMap", 		resultMap);
		model.addAttribute("currentPage", 		currentPage);
		model.addAttribute("loginHistoryList", 	resultMap.get("loginHistoryList"));
		model.addAttribute("lastPage", 			resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 		resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 		resultMap.get("endPageNum"));
		System.out.println(model + "model!!!!!!!!!!");
		return "login/loginHistory";
	}
	 
	//로그 아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/login";
	}
	
	//로그인
	@PostMapping("/login")
	public String login( @RequestParam(name = "userId", required = false) String userId
						,@RequestParam(name = "userPw", required = false) String userPw
						,HttpSession session) {
		
		User user = userService.getUserInfoById(userId);
		
		if(user != null) {
			String userPwCheck = user.getUserPw();
			if(user != null && userPwCheck.equals(userPw)) {
				session.setAttribute("SID"		, userId);
				session.setAttribute("SLEVEL"	, user.getUserLevel());
				session.setAttribute("SNAME"	, user.getUserName());
				
				// 이전 destination 불러오기
				// response.sendRedirect("/");        
				Object dest = (String)session.getAttribute("dest");
				log.info("dest : {}", dest);
				if(dest != null) {
					// 이전 destination으로 리디렉트
					return "redirect:"+dest;
				}
				return "redirect:/";
			}
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "로그인");
		return "login/login";
	}
	
	//아이디 찾기
	
	@PostMapping("/loginId")
	@ResponseBody
	public String isEmailCheck(@RequestParam(name = "userEmail" , required = false) String userEmail) {
		
		log.info("아이디 찾기 클릭시 요청받은 userEmail의 값: {}", userEmail);
		
		String result = userService.isEmailCheck(userEmail);
		System.out.println("!!! : "+ result);

		return result;
	}
	
	@GetMapping("/loginId")
	public String loginId(Model model) {
		model.addAttribute("title", "아이디 찾기");
		return "login/loginId";
	}	
	
	//비밀번호 찾기
	
	@PostMapping("/loginPw")
	@ResponseBody
	public String isIdCheck2(@RequestParam(name = "userId" , required = false) String userId) {
		
		log.info("비밀번호 찾기 클릭시 요청받은 userId의 값: {}", userId);
		
		String result = userService.isIdCheck2(userId);
		System.out.println("!!! : "+ result);

		return result;
	}
	
	@GetMapping("/loginPw")
	public String loginPw(Model model) {
		model.addAttribute("title", "비밀번호 찾기");
		return "login/loginPw";
	}	
	
	
}