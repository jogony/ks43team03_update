package ks43team03.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ks43team03.dto.FacilityGoods;
import ks43team03.dto.ResponseGoods;
import ks43team03.dto.User;
import ks43team03.exception.CustomException;
import ks43team03.exception.ErrorMessage;
import ks43team03.service.FacilityGoodsService;
import ks43team03.service.UserService;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/goods")
public class FacilityGoodsController {
	
	private final FacilityGoodsService facilityGoodsService;
	private final UserService userService;
	
	
	
	public FacilityGoodsController(FacilityGoodsService facilityGoodsService, UserService userService) {
		this.facilityGoodsService = facilityGoodsService;
		this.userService = userService;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	//== html  임시경로  매핑 ==//
	@GetMapping("/goods")
	public String goodsRead() {
		return "goods/goodsRead";
	}
	
	
	//== html  임시경로  매핑 ==//
	@GetMapping("/tempgoods")
	public String goodsTemp() {
		return "goods/tempGoods";
	}

}
