package ks43team03.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ks43team03.dto.Boomk;
import ks43team03.dto.Facility;
import ks43team03.dto.User;
import ks43team03.mapper.BoomkMapper;

@Service
@Transactional
public class BoomkService {
	
	private final BoomkMapper boomkMapper;
	
	public BoomkService(BoomkMapper boomkMapper) {
		this.boomkMapper = boomkMapper;
	}
	
	/**
	 * 회원 즐겨찾기 조회
	 */
	
	public List<Boomk> getBoomkList(String userId){
		List<Boomk> BoomkList = boomkMapper.getBoomkList(userId);
		
		return BoomkList;
	}
	
	
	/**
	 * 회원 즐겨찾기 등록
	 */
	public int addBoomk(Boomk boomk) {
		System.out.println("BoomkService/addBoomk");
		int result = boomkMapper.addBoomk(boomk);
	
		return result;
	}	
	
	/**
	 * 회원 즐겨찾기 삭제
	 */
	public int removeBoomk (Boomk boomk) {
		
		int result = boomkMapper.removeBoomk(boomk);
		
		return result;
	}
	
	/*
	  회원 즐겨찾기 취소 (N)
	 
	public void modifyBoomk(Boomk boomk) {
		boomkMapper.modifyBoomk(boomk);
	}
	 
	  회원 즐겨찾기 추가 (Y)
	
	public void modifyBoomk2(Boomk boomk) {
		boomkMapper.modifyBoomk2(boomk);
	}		
	*/
	
}