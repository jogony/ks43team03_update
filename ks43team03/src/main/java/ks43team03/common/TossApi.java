package ks43team03.common;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ks43team03.dto.PaymentResDto;
import ks43team03.exception.CustomException;
import ks43team03.exception.ErrorMessage;

@Component
public class TossApi {

	@Value("${test.client.api.key}")
	private String testClientApiKey;
	
	//@Value("${success.call.back.url}")
	private String successCallBackUrl;
//	@Value("${fail.call.back.url}")
	private String failCallBackUrl;
	
	 // TEST_SECRTE_KEY 노출 x
	@Value("${test.secret.api.key}")
    private String SECRET_KEY;
	
//	새로운 API 엔드포인트 추가
//	API 요청에 새로운 선택 파라미터 추가
//	API 요청에서 사용하는 필수 파라미터를 선택 파라미터로 변경
//	API 응답에 새로운 필드 추가
//	새로운 ENUM 추가
//	에러 메시지 변경
//	새로운 에러 코드 추가
//	새로운 웹훅 이벤트 추가
	
	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	
	
	private static final Logger log = LoggerFactory.getLogger(TossApi.class);

	public TossApi(ObjectMapper objectMapper,RestTemplate restTemplate) {
		this.objectMapper = objectMapper;
		this.restTemplate = restTemplate;
	}
	
	/**
	 *  토스 결제승인 메소드
	 */
	public PaymentResDto confirmPayment(String paymentKey, String orderId, Long amount) {
		try {
			HttpHeaders headers = new HttpHeaders();
			
			String secretKey = SECRET_KEY + ":";
			String encodingAuth = new String(Base64.getEncoder().encode(secretKey.getBytes()));
			headers.setBasicAuth(encodingAuth);
//	        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
	        log.info("headers data : {}", headers);
	        Map<String, String> payloadMap = new HashMap<>();
	        payloadMap.put("orderId", orderId);
	        payloadMap.put("amount", String.valueOf(amount));
	        log.info("payloadMap data : {}", payloadMap);
	        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);
	        
	       return restTemplate.postForEntity(
	    			"https://api.tosspayments.com/v1/payments/" + paymentKey, 
	    			request, 
	    			PaymentResDto.class
	        		).getBody();
		}catch (Exception e) {
			throw new CustomException(ErrorMessage.NOT_FOUND_PAYMENT);
		}
	}
	
	/**
	 * 결제 조회 메소드
	 */
	public PaymentResDto findTossPaymentsbyOrderId(String orderId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			String secretKey = SECRET_KEY + ":";
			String encodingAuth = new String(Base64.getEncoder().encode(secretKey.getBytes()));
			headers.setBasicAuth(encodingAuth);
//	        headers.setContentType(MediaType.APPLICATION_JSON);
			//PaymentResDto paymentResDto= restTemplate.getForEntity("https://api.tosspayments.com/v1/payments/orders/" + orderId, PaymentResDto.class).getBody();
			HttpEntity<String> request = new HttpEntity<>(headers);
			log.info("request 데이터 : {}", request);
			URI uri = URI.create("https://api.tosspayments.com/v1/payments/orders/" + orderId);
			log.info("uri ============================ : {}",uri);
			return restTemplate.exchange(
					  uri,
					  HttpMethod.GET,
					  request,
					  PaymentResDto.class
					).getBody();
		}catch (Exception e) {
			throw new CustomException(ErrorMessage.NOT_FOUND_PAYMENT);
		}
	}
}
