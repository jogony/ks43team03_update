package ks43team03.controller;

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

import ks43team03.dto.Area;
import ks43team03.dto.AreaCity;
import ks43team03.dto.AreaCityTown;
import ks43team03.dto.Facility;
import ks43team03.dto.FacilityUse;
import ks43team03.dto.MainCtg;
import ks43team03.mapper.AdminFacilityMapper;
import  ks43team03.service.AdminFacilityService;

@Controller
@RequestMapping("/adminFacility")
public class AdminFacilityController {
	
	private static final Logger log = LoggerFactory.getLogger(AdminFacilityController.class);

	private final AdminFacilityService adminFacilityService;
	private final AdminFacilityMapper adminFacilityMapper;
	public AdminFacilityController(AdminFacilityService adminFacilityService, AdminFacilityMapper adminFacilityMapper) {
		this.adminFacilityService = adminFacilityService;
		this.adminFacilityMapper = adminFacilityMapper;
	}
	
	
	
	
	/*검색*/
	@PostMapping("/adminFacilityList")
	public String getSearchFacilityList(@RequestParam(name="searchKey")String searchKey
									   ,@RequestParam(name="searchValue", required = false)String searchValue
									   ,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
									   ,Model model) {
		
		log.info("searchKey : {}", searchKey);
		log.info("searchValue : {}", searchValue);
		if("facilityNm".equals(searchKey)) {
			searchKey = "f.facility_nm";
		}else if("userId".equals(searchKey)) {
			searchKey = "f.user_id";
			
		}else if("areaNm".equals(searchKey)) {
			searchKey = "a.area_nm";
			
		}else if("cityNm".equals(searchKey)) {
			searchKey = "ac.city_nm";
			
		}else {
			searchKey = "act.town_nm";
			
		}
		
		Map<String, Object> resultMap = adminFacilityService.getSearchFacilityList(searchKey, searchValue, currentPage);
		
		log.info("resultMap : {}",resultMap);
		
		if(resultMap != null) {
			
		log.info("resultMap.get(\"adminFacilityList\") : {}",resultMap.get("adminFacilityList"));
		
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("adminFacilityList",		resultMap.get("adminFacilityList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		model.addAttribute("title", "전체 시설 목록 조회");
		
		}
		return "map/map2";
	}
	
	
	
	
	/*시설삭제화면*/
	@GetMapping("removeFacility")
	public String removeMember(Model model
							   ,@RequestParam(name = "userId", required = false) String userId
							   ,@RequestParam(name = "result", required = false) String result) {

		model.addAttribute("userId", userId);
		if(result != null) model.addAttribute("result", result);
		return "adminFacility/removeFacility";
		}
	

	/*시설수정처리*/
	 @PostMapping("/modifyFacility") 
	 public String modifyFacility(Facility facility) {
  
		 log.info("시설 정보 수정 폼 입력값 : {}", facility);
		 
		 adminFacilityService.modifyFacility(facility);
	 
	 return "redirect:/adminFacility/adminFacilityList";
	 }
	 
	 
	/*시설수정화면*/
	@GetMapping("/modifyFacility")
	public String modifyFacility(@RequestParam(value="facilityCd", required=false) String facilityCd
								,Model model) {
		
		log.info("시설 수정 화면");
		Facility facility = adminFacilityService.getAdminFacilityInfoByCd(facilityCd);
		
		
		List<FacilityUse> facilityUseList = adminFacilityService.getFacilityUseList();
		List<MainCtg> mainCtgList = adminFacilityService.getMainCtgList();
		List<Area> areaList = adminFacilityService.getAreaList();
		List<AreaCity> cityList = adminFacilityMapper.getAreaCityList(facility.getAreaCd());
		List<AreaCityTown> townList = adminFacilityMapper.getAreaCityTownList(facility.getCityCd());
		
		model.addAttribute("title", "시설 수정");
		model.addAttribute("facility", facility);	
		model.addAttribute("facilityUseList", facilityUseList);	
		model.addAttribute("mainCtgList", mainCtgList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("townList", townList);
		
		 return "adminFacility/modifyFacility"; 
	}
	
	/*시도 코드에 해당하는 시군구 카테고리 목록 불러 오기*/
	@PostMapping("/getCityCdList")
	@ResponseBody
	public List<AreaCity> getCityCdList(@RequestParam(value = "areaCd" , required = false) String areaCd){
		log.info("cityCdList 데이터 :{}",areaCd);
		return adminFacilityMapper.getAreaCityList(areaCd);
	}
	
	/*시군구 코드에 해당하는 읍면동 카테고리 목록 불러 오기*/
	@PostMapping("/getTownCdList")
	@ResponseBody
	public List<AreaCityTown> getTownCdList(@RequestParam(value = "cityCd" , required = false) String cityCd){
		return adminFacilityMapper.getAreaCityTownList(cityCd);
	}

	
	
	/*시설등록처리*/
	  @PostMapping("/addFacility") 
	  public String addFacility(Facility facility
			  					,@RequestParam(name="facilityCd", required = false) String facilityCd
			  					,@RequestParam MultipartFile[] facilityImgFile, Model model
			  					,HttpServletRequest request) {
		  log.info("시설등록화면에서 입력한 data : {}", facility);
		  
		  String serverName = request.getServerName();
			String fileRealPath = "";
			if("localhost".equals(serverName)) {				
				fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			}else {
				fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
			}
			
			adminFacilityService.addFacility(facility, facilityImgFile, fileRealPath);
			
		  return "redirect:/adminFacility/adminFacilityListById"; 
	  }

	
	/*시설등록화면*/
	@GetMapping("/addFacility")
	public String addFacility(Model model) {
		
		List<MainCtg> mainCtgList = adminFacilityService.getMainCtgList();
		List<Area> areaList = adminFacilityService.getAreaList();
		List<FacilityUse> facilityUseList = adminFacilityService.getFacilityUseList();
		
		model.addAttribute("title", "시설 등록");
		model.addAttribute("mainCtgList", mainCtgList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("facilityUseList", facilityUseList);
		
		return "adminFacility/addFacility";
	}
	
		
	
	
	
	
	//시설등록자가 본인시설조회
	@GetMapping("/adminFacilityListById")
	public String getAdminFacilityListById(Model model
										 ,HttpSession session) {
		String sessionId = (String) session.getAttribute("SID");
		log.info("시설조회 아이디 : {}", sessionId);
		
		List<Facility> adminFacilityListById = adminFacilityService.getAdminFacilityListById(sessionId);
		model.addAttribute("title", "내 시설 정보");
		model.addAttribute("adminFacilityListById", adminFacilityListById);
		return "adminFacility/adminFacilityListById";
	}
	
	
	//관리자가 시설목록조회
	@GetMapping("/adminFacilityList")
	public String getAdminFacilityList(Model model
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
		return "adminFacility/adminFacilityList";
	}

}
