package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TrainerSchedule {
	private String trScheduleCd;
	private String facilityCd;
	private String lessonCd;
	private String userId;
	private String lessonStartTime;
	private String lessonEndTime;
	private String lessonMemo;
}
