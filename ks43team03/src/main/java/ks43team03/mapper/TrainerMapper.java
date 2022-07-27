package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.TrainerCareer;
import ks43team03.dto.TrainerLicense;
import ks43team03.dto.TrainerProfile;

@Mapper
public interface TrainerMapper {
	
	//트레이너 정보 수정
	public int modifyTrainerProfile(TrainerProfile trainerProfile);
	
	//Map<String,String>로 트레이너 자격증 조회
	public TrainerProfile getTrainerLicenseInfoByMap(Map<String, String> paramMap);
	//Map<String,String>로 트레이너 경력 조회
	public TrainerProfile getTrainerCareerInfoByMap(Map<String, String> paramMap);
	//Map<String,String>로 트레이너 정보 조회
	public TrainerProfile getTrainerProfileInfoByMap(Map<String, String> paramMap);
	
	//트레이너 리스트 총 행 수
	public int getTrainerCount();
	
	//트레이너 리스트 조회
	public List<TrainerProfile> getTrainerList(Map<String, Object> searchMap);
	
	//트레이너 자격증 등록
	public int addTrainerLicense(List<TrainerLicense> trainerLicenseList);
	
	//트레이너 경력 등록
	public int addTrainerCareer(List<TrainerCareer> trainerCareerList);
	
	//트레이너 등록
	public int addtrainer(TrainerProfile trainerProfile);
	
	//트레이너 닉네임 중복 체크
	public boolean isNicknameCheck(String trainerNickname);
}
