package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TrainerResume {

	private String trainerResumeCd;//트레이너 지원서 코드									
	private String trainerCd; // 트레이너 등록 코드
	private String lessonOfferCd; //레슨 구인 코드
	private String userId;//트레이너 아이디
	private String resumeFile; //자기소개(첨부파일)
	private String resumeRegDate; //등록일자
	private String resumeApproveId; //승인자id
	private String resumeApproveDate;	//승인일자
	private String resumeApproveState; //승인여부
	private String resumeReason;	//승인또는 반려 사유
}
