package ks43team03.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ks43team03.dto.User;
import ks43team03.service.NotificationService;

@RestController
public class NotificationController {
    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
    	this.notificationService = notificationService;
    }
    
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(User user,
    							@RequestParam (name = "receiverId", required = false, defaultValue = "") String receiverId,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(user.getUserId(), receiverId, lastEventId);
    }
    @GetMapping(value = "/login", produces = "text/event-stream")
    public SseEmitter login(User user) {
    	return notificationService.login(user.getUserId());
    }
    @GetMapping(value = "/logout", produces = "text/event-stream")
    public void logout(User user) {
    	notificationService.logout(user.getUserId());
    }
}