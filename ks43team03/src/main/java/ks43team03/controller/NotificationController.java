package ks43team03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ks43team03.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @title 좋야요 한 유저 sse 연결
     */
    @GetMapping(value = "/like", produces = "text/event-stream")
    public SseEmitter subscribe(HttpServletRequest request,
    							@RequestBody Map<String,String> map,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
    	log.info("receiverId : {}", map.get("receiverId"));
    	log.info("Last-Event-ID : {}", lastEventId);
    	
        return notificationService.subscribe(map.get("sessionId"), lastEventId);
    }
    /**
     * @title 로그인 한 유저 sse 연결
     */
    @GetMapping(value = "/login", produces = "text/event-stream")
    public SseEmitter login(HttpServletRequest request) {

    	log.info("sessionId : {}", request);
    	String occurId = (String)request.getAttribute("SID");
    	return notificationService.login(occurId);
    }
}