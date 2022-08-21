package ks43team03.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import ks43team03.dto.BookingDto;
import ks43team03.service.BookingService;
import ks43team03.service.CommonService;

@Controller
@RequestMapping("/booking")
public class BookingController {

	
	private static final Logger log = LoggerFactory.getLogger(BookingController.class);

	
	
	
	@GetMapping("/addBooking")
	public String addBooking() {
		return "stadium/stadiumDetail";
	}
}
