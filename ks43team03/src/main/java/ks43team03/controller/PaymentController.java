package ks43team03.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ks43team03.dto.Payload;
import ks43team03.dto.PaymentResDto;
import ks43team03.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 
 * @class 결제 controller 
 *
 */

@Slf4j
@Controller
@RequestMapping("/pay")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
    @GetMapping("/success")
    public String confirmPayment(
            @RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount,
        Model model, RedirectAttributes reattr) throws Exception {
    	
    	
    	
    	PaymentResDto paymentResDto = paymentService.confirmPayment(paymentKey, orderId, amount);
    	
    	//model.addAttribute("payment", paymentResDto);
    	reattr.addAttribute("orderId", paymentResDto.getOrderId());
    	reattr.addAttribute("paymentKey", paymentResDto.getPaymentKey());
    	reattr.addAttribute("amount", paymentResDto.getTotalAmount());
    	reattr.addAttribute("userId", paymentResDto.getUserId());
    	return "redirect:/pay/thank-you";
    }

    /**
     * 결제후 리다렉트 되는 경로  
     */
    @GetMapping("/thank-you")
	public String orderSuccess(@RequestParam(name = "orderId")String orderId, 
							   @RequestParam(name = "paymentKey", required = false)String paymentKey, 
							   @RequestParam(name ="amount" ,required = false) Long amount,
							   @RequestParam(name = "userId", required = false)String userId
							   , Model model) {
		
		PaymentResDto payResDto = paymentService.findTossPaymentsbyOrderId(orderId);
		model.addAttribute("pay", payResDto);
		model.addAttribute("userId", userId);
		return "order/orderSuccess";
	}
    
    
    
	@GetMapping("/fail")
	public String failPayment(@RequestParam String message, @RequestParam String code, @RequestParam String orderId, Model model) {
	    log.info("=======결제실패=======");
	    paymentService.failPayment(orderId);
		model.addAttribute("message", message);
	    model.addAttribute("code", code);
	    return "fail";
	}
	
	@GetMapping("/virtual-account/callback")
	@ResponseStatus(HttpStatus.OK)
	public void handleVirtualAccountCallback(@RequestBody Payload payload) {
		log.info("payload 값 : {}", payload);
		paymentService.handleVirtualAccountCallback(payload);
		
	}

	
	
	
	
	
	//== 테스트 ==//
	public String pay() {
		return "paymentTest";
	}
}
