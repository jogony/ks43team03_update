package ks43team03.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Facility;
import ks43team03.dto.User;
import ks43team03.dto.UserLevel;
import ks43team03.mapper.UserMapper;

/**
 * @author ksmart
 * @RequiredArgsConstructor - lombok 의존성 주입
 */
@Service
@Transactional
public class UserService {

	private final UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	/*
	 * 시설 내 회원 승인
	 */
	public void modifyFacilityUserState(String facilityApproveState, String userId, String facilityCd) {
		
		userMapper.modifyFacilityUserState(facilityApproveState, userId, facilityCd);
		
	}
	
	/**
	 * 시설 내 회원 목록
	 * @return
	 */
	public List<Map<String, Object>> getFacilityUserList(List<Facility> adminFacilityListById){
		
		List<Map<String, Object>> facilityUserList = userMapper.getFacilityUserList(adminFacilityListById);
		
		if(facilityUserList != null) {
			for(Map<String, Object> userMap : facilityUserList) {
				String facilityApproveState = userMap.get("facilityApproveState").toString();
				
				if("Y".equals(facilityApproveState)) {
					userMap.put("facilityApproveState", "승인");
				}else {
					userMap.put("facilityApproveState", "미승인");
				}
			}
		}
		
		log.info("facilityUserList : {}", facilityUserList);
		
		return facilityUserList;
		
	}
	
	
	/**
	 * 회원정보 변경
	 * @param user
	 * @return
	 */
	public int modifyUser(User user) {
		
		int result = userMapper.modifyUser(user);
		
		return result;
	}
	
	/**
	 * 회원 가입
	 */
	public int addUser(User user) {
		
		int result = userMapper.addUser(user);
		
		return result;
	}
	
	/**
	 * 아이디 중복 체크
	 * @param userId
	 * @return
	 */
	public boolean isIdCheck(String userId) {
		
		boolean result = userMapper.isIdCheck(userId);
		
		return result;
	}
	
	
	/**
	 * 회원 전체 목록
	 * @return
	 */
	public Map<String, Object> getUserList(int currentPage){
		
		int rowPerPage = 10;
		
		double rowCount = userMapper.getUserCount();
		
		int lastPage = (int)Math.ceil(rowCount/rowPerPage);
		
		int startRow = (currentPage - 1)*rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		int startPageNum = 1;
		int endPageNum = 10;
		
		if(lastPage > 10) {
			if(currentPage >= 6) {
				startPageNum = currentPage - 4;
				endPageNum = currentPage + 5;
				
				if(endPageNum >= lastPage) {
					startPageNum = lastPage - 9;
					endPageNum = lastPage;
				}
			}
		}else {
			endPageNum = lastPage;
		}
		
		log.info("paramMap : {}", paramMap);
		
		List<Map<String, Object>> userList = userMapper.getUserList(paramMap);
		
		if(userList != null) {
			for(Map<String, Object> userMap : userList) {
				
				String userLevel = userMap.get("userLevel").toString();
				
				//equals 는 String에서 쓸 수 있으므로 null 인지 확인
				if(userLevel != null) {
					
					List<UserLevel> userLevelList = userMapper.getUserLevelList();
					
					for(UserLevel level : userLevelList) {
						if(level.getLevelNum().equals(userLevel)) {
							userMap.put("userLevel", level.getLevelName());
						}
					}
				}
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", 		lastPage);
		resultMap.put("userList",		userList);
		resultMap.put("startPageNum",	startPageNum);
		resultMap.put("endPageNum",		endPageNum);
		
		return resultMap;
		
	}

	/**
	 * 로그인 이력 조회 (페이징 처리)
	 */
	public Map<String, Object> getLoginHistory(int currentPage){
		// 몇개 행 노출
		int rowPerPage = 5;
		int startPageNum = 1;
		int endPageNum = 10;
		
		// 총 행의 갯수
		double rowCount = userMapper.getLoginHistoryCount();
		
		// 마지막페이지
		int lastPage = (int) Math.ceil(rowCount/rowPerPage); /*더블로 되어있어서 int*/
		
		// 페이징 처리
		int startRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> loginHistoryList = userMapper.getLoginHistory(paramMap);
		
		//동적 페이지번호 - currentPage가 뭐뭐 보다 클때 고정되어있던 페이지 번호가 움직인다.
		if(currentPage > 6) {
			startPageNum = currentPage - 5;
			endPageNum = currentPage + 4;
			
			if(endPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", lastPage);
		resultMap.put("loginHistoryList", loginHistoryList);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);
		
		return resultMap;
	}
				 

	/**
	 * 회원 상세정보
	 */
	public User getUserInfoById(String userId) {

		User user = userMapper.getUserInfoById(userId);

		return user;
	}
	
	/**
	 * 아이디 찾기
	 */
	
	public String isEmailCheck(String userEmail) {

		String result = userMapper.isEmailCheck(userEmail);

		return result;
	}
	
	/**
	 * 비밀번호 찾기
	 */
	
	public String isIdCheck2(String userId) {
		
		String result = userMapper.isIdCheck2(userId);
		
		return result;
	}
	
	
}
