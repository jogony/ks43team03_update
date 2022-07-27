package ks43team03.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ks43team03.dto.Facility;
import ks43team03.service.AdminFacilityService;


@Controller
public class MainController {
	
	private final AdminFacilityService adminFacilityService;

	public MainController(AdminFacilityService adminFacilityService) {
		this.adminFacilityService = adminFacilityService;
	}
	
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	
	@GetMapping("/user")
	public String adminIndex() {
		return "index";
	}
	
	@GetMapping("/")
	public String index() {
		return "admin/adminIndex";
	}
	
	/**
	 *임시 addGoods 컨트롤러  
	 * @throws IOException 
	 ***/
	@GetMapping("/admin/addGoods")
	public String addGoods(@RequestParam(name="goodsCtgCd", required = false) String goodsCtgCd
							,HttpServletResponse response
							,HttpSession session
							,Model model) throws IOException {
		String sessionId = (String)session.getAttribute("SID");
		List<Facility> adminFacilityListById = adminFacilityService.getAdminFacilityListById(sessionId);

		if(adminFacilityListById.size() == 0) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = null;
			
			out = response.getWriter();
			
		
			out.println("<script>alert('등록된 시설이 없습니다.'); location.href='/';</script>");
			out.flush();
		
		
		}
		model.addAttribute("goodsCtgCd", goodsCtgCd);
		model.addAttribute("adminFacilityListById", adminFacilityListById);
		
		return "admin/addGoods";
	}
	
	/**
	 * 임시 map 컨트롤러
	 */
	@GetMapping("/map")
	public String searchMap() {
		return "map/map";
	}
	
	@GetMapping("/map2")
	public String searchMap2(Model model
			  				,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage){
		
		Map<String, Object> resultMap = adminFacilityService.getAdminFacilityList(currentPage);
		
		log.info("resultMap : {}",resultMap);
		log.info("resultMap.get(\"adminFacilityList\") : {}",resultMap.get("adminFacilityList"));
		
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("adminFacilityList",		resultMap.get("adminFacilityList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("title", "전체 시설 목록 조회");
		return "map/map2";
  }
	
	
	@GetMapping("/cal")
	public String cal() {
		return "order/cal";
  }
	@GetMapping("/admin/ex")
	public String ex() {
		return "ex";

	}
}
