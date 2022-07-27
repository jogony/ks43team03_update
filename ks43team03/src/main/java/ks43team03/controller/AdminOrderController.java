package ks43team03.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ks43team03.dto.Facility;
import ks43team03.dto.Order;
import ks43team03.dto.OrderSearchDto;
import ks43team03.dto.PageDto;
import ks43team03.service.AdminFacilityService;
import ks43team03.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

	private final OrderService orderService;
	private final AdminFacilityService adminFacilityService;
	
	private static final Logger log = LoggerFactory.getLogger(AdminOrderController.class);

	
	public AdminOrderController(OrderService orderService, AdminFacilityService adminFacilityService) {
		this.orderService = orderService;
		this.adminFacilityService = adminFacilityService;
	}
	
	@GetMapping("/order/orderList")
	public String adminOrderList(Model model,HttpSession session) {
		
		// 임시로 user권한은 시설관리자만 조회 
		String userId = (String)session.getAttribute("SID");
		String level = (String)session.getAttribute("SLEVEL");
		
		log.info("권한은 : {}", level);
		if(!"1".equals(level)) {
			List<Facility> facility = adminFacilityService.getAdminFacilityListById(userId);
			model.addAttribute("facility", facility);
			
		}
		model.addAttribute("title", "주문관리");
		
		return "admin/order/adminOrderList";
	}
	
	
	/**
	 * loading시 요청하는 ajax 
	 * 
	 *   facility Cd 받음 :: 
	 */
	
	@GetMapping("/order/search")
	public String facilityOrderList(@RequestParam(required = false, defaultValue = "1")int currentPage, OrderSearchDto order ,Model model){
		
		String level = order.getLevel();
		if(!"1".equals(level) && order.getFacilityCd() == null) {
			return "admin/order/adminOrderList :: #orderList";
		}
		
		PageDto<Order> page  = orderService.getSearchOrderList(order,currentPage);
		log.info("orderList : {}", page);
		model.addAttribute("orderList", page.getList());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", page.getLastPage());
		model.addAttribute("startPage", page.getStartPage());
		model.addAttribute("endPage", page.getEndPage());
		model.addAttribute("title", "회원님의 주문내역입니다.");
		return "admin/order/adminOrderList :: #orderList";
	}
	
	
	/*
	 * 주문내역 
	 */
	@GetMapping("/order/orderDetail")
	public String orderDetail(@RequestParam(name = "orderCd")String orderCd,
							@RequestParam(name = "goodsCtgCd", required = false)String goodsCtgCd,
							Model model) {
		log.info("화면에서 받은  orderCd 데이터 : {}",orderCd);
		log.info("화면에서 받은  goodsCtgCd 데이터 : {}",goodsCtgCd);
		Order order = orderService.getOrderDetail(orderCd);
		model.addAttribute("order", order);
		return "admin/order/orderDetail";
	}
	
	
//	@GetMapping("/order/search")
//	public String orderSearch(@RequestParam(name = "date-after",required = false)String afterDate,@RequestParam(name = "date-before",required = false)String beforeDate,
//			@RequestParam(name = "facilityName",required = false)String facilityName,Model model) {
//		
//		log.info("date-before {}", beforeDate);
//		log.info("after-before {}", afterDate);
//		return "admin/order/adminOrderList";
//	}
}
