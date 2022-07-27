package ks43team03.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ks43team03.dto.Point;
import ks43team03.mapper.PointMapper;

@Service
public class PointService {

	public final PointMapper pointMapper;
	
	public PointService(PointMapper pointMapper) {
		this.pointMapper = pointMapper;
	}
	
	// 회원 포인트 조회
	public List<Point> getPointList(String userId){
		List<Point> pointList = pointMapper.getPointList(userId);
		return pointList;
	}
	
	// 포인트 합계
	public String getTotalPoint(String userId){
		return pointMapper.getTotalPoint(userId);
	}
	
}