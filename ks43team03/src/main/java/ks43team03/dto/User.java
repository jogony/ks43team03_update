package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String userId;
	private String userLevel;
	private String userPw;
	private String userName;
	private String userAddr;
	private String userBirth;
	private String userEmail;
	private String userTell;
	private String userGender;
	private String userDrop;
	private String userRegPath;
	private String userTerms;
	private String userRegDate;
	private String userPostNum;
	private String userDetailAddr;
	
	private UserLevel levelInfo;
}
