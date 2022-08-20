package ks43team03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification {

    private Long id;

    
    private String content;

    
    private String url;

    private Boolean isRead;

    private String notificationType;

    private String receiver;

	public Notification(String receiver, String content) {
		this.receiver = receiver;
		this.content = content;
	}
}