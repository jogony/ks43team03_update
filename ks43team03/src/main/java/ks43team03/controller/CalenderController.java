package ks43team03.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ks43team03.dto.Lesson;
import ks43team03.service.CalenderService;
import ks43team03.service.CommonService;

@RestController
@RequestMapping("/calendar")
public class CalenderController {
	
	
	private static final Logger log = LoggerFactory.getLogger(CalenderController.class);
	private final CalenderService calenderService;
	private final CommonService commonService;
	public CalenderController(CalenderService calenderService, CommonService commonService) {
		this.calenderService = calenderService;
		this.commonService   = commonService;
	}
	
	/**
	 *  예약 받기
	 **/
	@PostMapping("/reservation")
	public String reservation(@RequestBody Map<String, String> reservationData, HttpSession session) {
		Map<String, String> dataMap = new HashMap<>();
		String data = "";
		reservationData.put("userId", (String)session.getAttribute("SID"));
		
		log.info("reservationData : {}", reservationData);
		String result = Integer.toString(commonService.setRservation(reservationData));
		dataMap.put("result", result);
		
		ObjectMapper om = new ObjectMapper();
		
		try {
			data = om.writeValueAsString(dataMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	/**
	 *  캘린더 일정 보내기
	 **/
	@PostMapping("/scheduleData")
	public String scheduleData(@RequestBody Map<String, String> scheduleDate) {
		String scheduleJson = "";
		log.info("scheduleDate : {}" ,scheduleDate);


		ObjectMapper om = new ObjectMapper();
		try {
			log.info("date : {}", calenderService.findSearch(scheduleDate));
			Map<String, Object> dateMap = calenderService.findSearch(scheduleDate);
			scheduleJson = om.writeValueAsString(dateMap.get("scheduleDate"));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(scheduleJson);
		return scheduleJson;
	}
}
