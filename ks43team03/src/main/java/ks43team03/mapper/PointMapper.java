package ks43team03.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Point;

@Mapper
public interface PointMapper {
	
	// 회원 포인트 조회
	public List<Point> getPointList(String userId);
	
	// 포인트 합계
	public String getTotalPoint(String userId);
	
	// 리뷰 포인트 적립
	public String reviewPointGet(String userId);
}