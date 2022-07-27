package ks43team03.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardComment {

	private String boardPostCommentCd;
	private String boardPostCd;
	private String boardCtgCd;
	private String userId;
	private String boardPostContentDetail;
	private Date boardPostCommentDate;
	private char  boardPostCommentState;
	
}
