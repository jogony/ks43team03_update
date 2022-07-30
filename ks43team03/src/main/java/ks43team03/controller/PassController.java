package ks43team03.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ks43team03.dto.Facility;
import ks43team03.dto.Pass;
import ks43team03.service.AdminFacilityService;
import ks43team03.service.FacilityGoodsService;
import ks43team03.service.PassService;

/**
 * 
 * @author 
 * 이용권 Controller
 *
 */

@Controller
@RequestMapping("/pass")
public class PassController {
	
	
	private static final Logger log = LoggerFactory.getLogger(PassController.class);

	private final FacilityGoodsService facilityGoodsService;
	private final PassService passService;
	private final AdminFacilityService adminFacilityService;
	
	public PassController(FacilityGoodsService facilityGoodsService,PassService passService,AdminFacilityService adminFacilityService ) {
		this.facilityGoodsService = facilityGoodsService;
		this.passService = passService;
		this.adminFacilityService = adminFacilityService;
	}

	@PostMapping("/addPass")
	public String addPass(Pass pass) {
		passService.addPass(pass);
		return "redirect:/pass/admin/passList";
	}
	
	@GetMapping("/addPass")
	public String addPass(@RequestParam(name = "userId", required = false)String userId) {
		
		//권한이 시설관리자인지 아닌지 확인 
		
		return "pass/addPass";
	}
	
	//== 관리자 이용권파트 ==//
	/**
	 * 관리자 이용권리스트
	 * 
	 */
	@GetMapping("/admin/passList")
	public String adminPassList(Model model) {
		
		//권한이 관리자인지 아닌지 체크
		
		//페이징 처리
		
		
		//전체이용권리스트
		List<Pass> passList = passService.getPassAll();
		model.addAttribute("title", "이용권리스트 관리자");
		model.addAttribute("passList", passList);
		return "pass/adminPassList";
	}
	
	
	/**
	 * 관리자 이용권상세정보
	 * 
	 */
	@GetMapping("/passDetail")
	public String amdinPassDetail(@RequestParam(name = "passCd")String passCd,
							 @RequestParam(name = "facilityGoodsCd", required = false)String facilityGoodsCd,
							 Model model) {
		log.info("화면에서 받은 facilityGoodsCd 데이터 :  {}", facilityGoodsCd);
		log.info("화면에서 받은 passCd 데이터 :  {}", passCd);
		
		Pass pass = passService.getPassDetail(passCd, facilityGoodsCd);
		log.info("요청처리의 응답 데이터 : {}", pass);
		
		model.addAttribute("title", "이용권상세정보");
		model.addAttribute("pass", pass);
		
		return "pass/passDetail";
	}
	
	
	
	
	//== 시설주 이용권 리스트 ==//
	/**
	 * 이용권 리스트
	 * 리펙토링 해야겠다
	 */
	@GetMapping("/passList")
	public String passList(Model model, HttpSession session) {
		String userId = (String)session.getAttribute("SID");
		//페이징 처리
		List<Facility> facilityList = adminFacilityService.getAdminFacilityListById(userId);
		//String facilityCd = "ss_35011770_03";
		List<Map<String,Object>> result = new ArrayList<>();
		for(Facility facility : facilityList) {
			Map<String,Object> map = new HashMap<String, Object>();
			List<Pass> passList = passService.getPassListOfFacility(facility.getFacilityCd());
			map.put("passList", passList);
			map.put("facility", facility);
			result.add(map);
			
		}
		model.addAttribute("title", "시설이용권 리스트");
		model.addAttribute("result", result);
		return "pass/passList";
	}
	
	/**
	 * 이용권 수정
	 */
	@GetMapping("/modifyPass")
	public String modifyPass(@RequestParam(name = "passCd" ,required = false)String passCd,
							 @RequestParam(name = "userId" ,required = false)String userId,
							 @RequestParam(name = "facilityGoodsCd" , required = false)String facilityGoodsCd,
							 Model model) {
		//시설에 있는 관리자 정보가 맞다면
		log.info("화면에서 받은 passCd데이터 : {}",passCd);
		log.info("화면에서 받은 userId데이터 : {}",userId);
		log.info("화면에서 받은 facilityGoodsCd데이터 : {}",facilityGoodsCd);
		
		Pass pass = passService.getPassDetail(passCd, facilityGoodsCd);
		
		model.addAttribute("title", "시설이용권 수정");
		model.addAttribute("pass",pass);
		
		return "pass/modifyPass";
	}
	
	@PostMapping("/modifyPass")
	public String modifyPass(Pass pass) {
		log.info("화면에서 받은 pass data: {}",pass);
		log.info("화면에서 받은 pass.getPassCd() data: {}",pass.getPassCd());
		log.info("화면에서 받은 pass.getPassNm() data: {}",pass.getPassNm());
		passService.modifyPass(pass);
		return "redirect:/pass/passList";
	}
	
	
	/**
	 * 이용권 삭제
	 */
	
	@GetMapping("/removePass")
	public String removePass(@RequestParam(name = "passCd" ,required = false)String passCd,
							 @RequestParam(name = "userId" ,required = false)String userId,
							 @RequestParam(name = "facilityGoodsCd" , required = false)String facilityGoodsCd){
		
		return "pass/removePass";
	}
	
	
	@PostMapping("/facility")
	@ResponseBody
	public List<Facility> getFacility(@RequestParam("userId") String userId){
		log.info("userId : data {}", userId);
		List<Facility> facilityList = adminFacilityService.getAdminFacilityListById(userId);
		return facilityList;
	}
	
	
	
	@PostMapping("/removePass")
	public boolean removePass(@RequestBody String passCd) {
		
		
		return false;
	}
	
	
	
	
//	@PostMapping("/addPass2")
//	public String addPass2(Pass pass) {
//		log.info("pass값에 담긴갑 : {}", pass.getGoodsCtgCd());
//		log.info("abGoods test", pass);
//		
//		
//		return null;
//	}
	
	
}
