package ks43team03.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import ks43team03.common.FileUtils;
import ks43team03.dto.TrainerCareer;
import ks43team03.dto.TrainerLicense;
import ks43team03.dto.TrainerProfile;
import ks43team03.dto.User;
import ks43team03.mapper.FileMapper;
import ks43team03.mapper.TrainerMapper;
import ks43team03.mapper.UserMapper;

@Transactional
@Service
public class TrainerService {
	
	
	private static final Logger log = LoggerFactory.getLogger(TrainerService.class);

	private final FileMapper fileMapper;
	private final TrainerMapper trainerMapper;
	private final UserMapper userMapper;
	
	public TrainerService(FileMapper fileMapper, TrainerMapper trainerMapper, UserMapper userMapper) {
		this.fileMapper = fileMapper;
		this.trainerMapper = trainerMapper;
		this.userMapper = userMapper;
	}
	
	/**
	 * 자격증 코드로 자격증 조회
	 */
	public TrainerProfile getTrainerLicenseByTrainerLicenseCd(String trainerLicenseCd) {
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("trainerLicenseCd", trainerLicenseCd);
		
		TrainerProfile trainerLicense = trainerMapper.getTrainerLicenseInfoByMap(paramMap);
		
		return trainerLicense;
	}
	
	/**
	 * 경력 코드로 경력 조회
	 */
	public TrainerProfile getTrainerCareerByTrainerCareerCd(String trainerCareerCd) {
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("trainerCareerCd", trainerCareerCd);
		
		TrainerProfile trainerCareer = trainerMapper.getTrainerCareerInfoByMap(paramMap);
		
		return trainerCareer;
	}
	
	/**
	 * 트레이너 프로필 수정
	 */
	public int modifyTrainerProfile(TrainerProfile trainerProfile) {
		
		int result = trainerMapper.modifyTrainerProfile(trainerProfile);
		
		return result;
	}
	
	/**
	 * 유저 아이디로 트레이너 프로필 조회
	 */
	public TrainerProfile getTrainerProfileByUserId(String userId) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		
		TrainerProfile trainerProfile =null;
		
		trainerProfile = trainerMapper.getTrainerProfileInfoByMap(paramMap);
		
		return trainerProfile;
	}
	
	/**
	 * 트레이너 paramMap 으로 트레이너 정보 조회
	 * @param trainerCd
	 * @return
	 */
	public Map<String, Object> getTrainerInfoByTrainerCd(Map<String, String> paramMap) {
		
		TrainerProfile	trainerProfile	= trainerMapper.getTrainerProfileInfoByMap(paramMap);
		TrainerProfile	trainerCareer	= trainerMapper.getTrainerCareerInfoByMap(paramMap);
		TrainerProfile	trainerLicense	= trainerMapper.getTrainerLicenseInfoByMap(paramMap);
		
		Map<String, Object> trainerMap = new HashMap<String, Object>();
		
		trainerMap.put("trainerProfile",	trainerProfile);
		trainerMap.put("trainerCareer",		trainerCareer);
		trainerMap.put("trainerLicense",	trainerLicense);
		
		return trainerMap;
	}
	
	/**
	 * 트레이너 자격증 등록
	 */
	public int addTrainerLicense(List<TrainerLicense> trainerLicenseList, String userId, String fileRealPath) {
		
		for(int i=0; i<trainerLicenseList.size(); i++) {
			//파일 없다면 거치지 않도록
			if(!ObjectUtils.isEmpty(trainerLicenseList.get(i).getTrainerLicenseFiles())) {
				
				//파일 업로드 위한 객체 생성
				FileUtils fu = new FileUtils(trainerLicenseList.get(i).getTrainerLicenseFiles(), userId, fileRealPath);
				List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			
				// t_file 테이블에 삽입
				log.info("addCareer dtoFileList : {}", dtoFileList);
		        fileMapper.uploadFile(dtoFileList);
		        
		        //file_cd삽입
		        trainerLicenseList.get(i).setTrainerLicenseFile(dtoFileList.get(0).get("fileCd"));
			}
		}
		
		//TrainerLicense 테이블 삽입
		int result = trainerMapper.addTrainerLicense(trainerLicenseList);
		log.info("result : {}", result);
		
		return result;
	}

	/**
	 * 트레이너 경력 등록
	 */
	public int addTrainerCareer(List<TrainerCareer> trainerCareerList, String userId, String fileRealPath) {
		log.info("trainerCareerList : {} ",trainerCareerList);
		log.info("userId : {} ",userId);
		log.info("fileRealPath : {} ",fileRealPath);
		
		for(int i=0; i<trainerCareerList.size(); i++) {
			//파일 없다면 거치지 않도록
			if(!ObjectUtils.isEmpty(trainerCareerList.get(i).getTrainerCareerFiles())) {
				
				//파일 업로드 위한 객체 생성
				FileUtils fu = new FileUtils(trainerCareerList.get(i).getTrainerCareerFiles(), userId, fileRealPath);
				List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			
				// t_file 테이블에 삽입
				log.info("addCareer dtoFileList : {}", dtoFileList);
		        fileMapper.uploadFile(dtoFileList);
		        
		        //file_cd삽입
		        trainerCareerList.get(i).setTrainerCareerFile(dtoFileList.get(0).get("fileCd"));
			}
		}
		//TrainerCareer테이블 삽입
		int result = trainerMapper.addTrainerCareer(trainerCareerList);
		log.info("result : {}", result);
		
		return result;
	}
	
	/**
	 * 트레이너 등록 및 권한 변경 후 코드 반환
	 * @param trainerProfile 
	 * @param fileRealPath 
	 * @param trainerImgFile 
	 */
	public String addtrainer(TrainerProfile trainerProfile, MultipartFile[] trainerImgFile, String fileRealPath) {
		
		// uproaderId
		String userId = trainerProfile.getUserId();
		
		// 트레이너 등록 - 트레이너 코드 selectKey로 담아 줌
		trainerMapper.addtrainer(trainerProfile);
		
		//파일 업로드 위한 객체 생성 
		FileUtils fu = new FileUtils(trainerImgFile, userId, fileRealPath);
		List<Map<String, String>> dtoFileList = fu.parseFileInfo();
		
		// t_file 테이블에 삽입
		log.info("addTrainer dtoFileList : {}", dtoFileList);
        fileMapper.uploadFile(dtoFileList);
		
		log.info("add 이후 trainerProfile : {}", trainerProfile);
		
		String trainerCd = trainerProfile.getTrainerCd();
		log.info("trainerCd : {}", trainerCd);
		
		// userId로 User 맴버 확인
		User user = userMapper.getUserInfoById(userId);
		
		int userLevel = Integer.parseInt(user.getUserLevel());
		
		// User맴버에서 권한 변경
		if(userLevel > 4) {
			user.setUserLevel("4");
			userMapper.modifyUser(user);
		}
		
		// 릴레이션 테이블에 삽입
		List<Map<String, String>> relationFileList = new ArrayList<>();
		for(Map<String, String> m : dtoFileList) {
			m.put("trainerCd", trainerCd);
			relationFileList.add(m);
		}
		log.info("relationFileList", relationFileList);
		fileMapper.uploadRelationFileWithTrainer(relationFileList);
		
		return trainerCd;
	}
	
	/**
	 * 닉네임 중복 체크
	 * @param userId
	 * @return
	 */
	public boolean isNicknameCheck(String trainerNickname) {
		
		boolean result = trainerMapper.isNicknameCheck(trainerNickname);
		
		return result;
	}
}
