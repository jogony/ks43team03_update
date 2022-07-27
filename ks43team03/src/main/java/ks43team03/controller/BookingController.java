package ks43team03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ks43team03.dto.BookingDto;
import ks43team03.service.BookingService;

@Controller
@RequestMapping("/booking")
public class BookingController {

	private final BookingService bookingService;
	
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	
	@PostMapping("/addBooking")
	public String addBooking(BookingDto booking) {
		bookingService.addBooking(booking);
		return "redirect:/stadium/stadiumDetail";
	}
	
	
	
	@GetMapping("/addBooking")
	public String addBooking() {
		return "stadium/stadiumDetail";
	}
}
