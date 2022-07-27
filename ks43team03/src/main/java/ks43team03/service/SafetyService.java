package ks43team03.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import ks43team03.common.FileUtils;
import ks43team03.dto.Safety;
import ks43team03.mapper.FileMapper;
import ks43team03.mapper.SafetyMapper;



@Service
@Transactional
public class SafetyService {
	
	private final SafetyMapper safetyMapper;
	private final FileMapper fileMapper;
	
	public SafetyService(SafetyMapper safetyMapper, FileMapper fileMapper) {
		this.safetyMapper = safetyMapper;
		this.fileMapper = fileMapper;		
	}

	/**
	 * 안전점검 등록
	 */
	public void addSafety(Safety safety, MultipartFile[] uploadfile, String fileRealPath, Object oUserId) {
		
		//파일이 널이 아니라면
		if (!ObjectUtils.isEmpty(uploadfile)) {
			String userId = String.valueOf(oUserId);
			String uproaderId 	= userId;
			safety.setUserId(userId);
			/***
			 * test code: start
			 ***/
			
			FileUtils fu = new FileUtils(uploadfile, uproaderId, fileRealPath);
			List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			// 1. t_file 테이블에 삽입
			System.out.println(dtoFileList + "SafetyService/addSafety");
			fileMapper.uploadFile(dtoFileList);
			/***
			 * test code: end
			 ***/
			// 2. safety 테이블에 삽입

			System.out.println(safety + "SafetyService/addSafety/safety");
			safetyMapper.addSafety(safety);
			
	
	    }

	}	
	
	
	/*
	public int addSafety(Safety safety) {
		
		int result = safetyMapper.addSafety(safety);
		
		return result;
	}
	*/

	/**
	 * 관리자의 안전점검 등록 시설 목록 조회(전체) 
	 */
	
	public Map<String, Object> getSafetyList(int currentPage) {
		// 몇개 행 노출
		int rowPerPage = 5;
		int startPageNum = 1;
		int endPageNum = 10;
		
		// 총 행의 갯수
		double rowCount = safetyMapper.getSafetyListCount();
		
		// 마지막페이지
		int lastPage = (int) Math.ceil(rowCount/rowPerPage); /*더블로 되어있어서 int*/
		
		// 페이징 처리
		int startRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> safetyList = safetyMapper.getSafetyList(paramMap);
		
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
		resultMap.put("safetyList", safetyList);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);
		
		return resultMap;
	}
	
	/**
	 * 시설 관리자의 안전점검 등록 목록 조회
	 */
	
	public List<Safety> getSafetyListById(String userId) {
		List<Safety> safetyListById = safetyMapper.getSafetyListById(userId);
		
		return safetyListById;
	}
	
	/**
	 * 안전점검 등록 정보 수정
	 */
	
	public int modifySafety(Safety safety) {
		return safetyMapper.modifySafety(safety);
	}

	/**
	 * 안전점검 등록코드별 시설 수정
	 */
	
	public Safety getSafetyInfoByCd(String safetyCheckCd) {
		return safetyMapper.getSafetyInfoByCd(safetyCheckCd);
	}	
	
	/**
	 * 안전점검 등록 정보 삭제
	 */
	public int removeSafety(Safety safety) {
		
		int result = safetyMapper.removeSafety(safety);
		
		return result;
		
	}

	/**
	 * 시설 관리자의 안전점검 등록 정보 삭제
	 */
	public int removeSafety2(Safety safety) {
		
		int result = safetyMapper.removeSafety2(safety);
		
		return result;
		
	}
	
	
	/**
	 * 관리자의 안전점검 결과 목록 조회(전체) 
	 */
	
	public Map<String, Object> getSafetyResultList(int currentPage) {
		// 몇개 행 노출
		int rowPerPage = 5;
		int startPageNum = 1;
		int endPageNum = 10;
		
		// 총 행의 갯수
		double rowCount = safetyMapper.getSafetyResultListCount();
		
		// 마지막페이지
		int lastPage = (int) Math.ceil(rowCount/rowPerPage); /*더블로 되어있어서 int*/
		
		// 페이징 처리
		int startRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> safetyResultList = safetyMapper.getSafetyResultList(paramMap);
		
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
		resultMap.put("safetyResultList", safetyResultList);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);
		
		return resultMap;
	}
	
	/**
	 * 시설 관리자의 안전점검 결과 목록 조회 
	 */
	
	public List<Safety> getSafetyResultListById(String userId) {
		List<Safety> getSafetyResultListById = safetyMapper.getSafetyResultListById(userId);
		
		return getSafetyResultListById;
	}

	
	
	
	
	

	
}