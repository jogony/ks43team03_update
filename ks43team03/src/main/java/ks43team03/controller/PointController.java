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

import ks43team03.dto.Point;
import ks43team03.service.PointService;

@Controller
@RequestMapping("/point")
public class PointController {

	private static final Logger log = LoggerFactory.getLogger(PointController.class);
	
	private final PointService pointService;
	
	public PointController(PointService pointService) {
		this.pointService = pointService;
	}
	
	//회원 포인트 조회
	@GetMapping("/pointList")
	public String getPointList(Model model
							, @RequestParam(name = "userId", required = false) String userId
							, HttpSession session) {
		String sessionId = (String)session.getAttribute("SID");
		List<Point> pointList = pointService.getPointList(sessionId);
		String totalPoint = pointService.getTotalPoint(sessionId);
		
		model.addAttribute("title", "포인트 조회");
		model.addAttribute("pointList", pointList);
		model.addAttribute("totalPoint", totalPoint);
		
		log.info("pointList : {}", pointList);
		log.info("totalPoint : {}", totalPoint);
		
		return "point/pointList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}