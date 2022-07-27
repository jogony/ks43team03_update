package ks43team03.service;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;	
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ks43team03.common.EmitterRepository;
import ks43team03.dto.Boomk;

@Service
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final EmitterRepository emitterRepository;

    public NotificationService(EmitterRepository emitterRepository) {
        this.emitterRepository = emitterRepository;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                                   .id(id)
                                   .name("sse")
                                   .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
    }

    /*
    public void send(String receiver, Boomk boomk, String content) {
        Notification notification = createNotification(receiver, boomk, content);
        String id = String.valueOf(receiver);
        
        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByuserId(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 데이터 캐시 저장(유실된 데이터 처리하기 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, NotificationResponse.from(notification));
                }
        );
    }
	*/

    public SseEmitter subscribe(String userId, String lastEventId) {
        
        String id = userId + "_" + System.currentTimeMillis();
        
        
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        log.info("emitter : {}" ,emitter);
        
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));


        // 503 에러를 방지하기 위한 더미 이벤트 전송
        log.info("503 에러를 방지하기 위한 더미 이벤트 전송");
        sendToClient(emitter, id, "EventStream Created. [userId=" + userId + "]");
        log.info("503 에러를 방지하기 위한 더미 이벤트 전송 완료");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByuserId(String.valueOf(userId));
            events.entrySet().stream()
                  .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                  .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }



	public SseEmitter login(String userId) {
		String id = userId + "_" + System.currentTimeMillis();
        
        
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
        log.info("emitter : {}" ,emitter);
        
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));


        // 503 에러를 방지하기 위한 더미 이벤트 전송
        log.info("503 에러를 방지하기 위한 더미 이벤트 전송");
        sendToClient(emitter, id, "로그인 되었습니다.");
        log.info("503 에러를 방지하기 위한 더미 이벤트 전송 완료");
		return emitter;
		
	}
}