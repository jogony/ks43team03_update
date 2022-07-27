package ks43team03.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Order;
import ks43team03.dto.OrderSearchDto;
import ks43team03.dto.PageDto;

@Mapper
public interface OrderMapper {
	
	// 주문 생성메소드
	public void addOrder(Order order);
	
	// 주문기본키로  주문정보를 보는 메소드
	public Optional<Order> getOrderByCode(String orderCd);
	
	// 주문 uuid로 주문정보를 보는 메소드 
	public Optional<Order> getOrderByOrderUUID(String orderId);
	
	// 주문코드 주문및결제정보 
	public Order getOrderAndPayInfomationByOrderCd(String orderCd);
	
	// 주문 상품 상세조회
	public Optional<Order> getOrderDetailByOrderCd(String orderCd);
	
	//대관 주문내역 상세
	public Order getOrderDetailWithStadium(String orderCd);
	//레슨 주문내역 상세
	public Order getOrderDetailWithLesson(String orderCd);
	//이용권 주문내역 상세
	public Order getOrderDetailWithPass(String orderCd);
	
	public List<Order> orderInfomationByCategory(String category, String userId);
	
	// 주문정보 수정
	public int modifyOrder(Order order);
	
	
	
	// 회원 한명의 주문내역 조회 메소드
//	public List<Order> getOrdersByUser(Map<String, Object> maps);
	public List<Order> getOrdersByUser(String userId,String dateMonth,String searchValue);
	
	// 주문 삭제 uuid 
	public int removeOrderByOrderUUID(String orderUUID);
	
	// 주문삭제 기본키
	public int removeOrderByOrderCd(String orderCd);
	
	// 회원한명의 주문카운트
	public int getOrderByUserCount(String userId);
	
	/**
	 * 관리자
	 */
	
	// 시설의 주문정보를 조회
	public List<Order> getOrderInfoForFacility(PageDto<Order> faciltiyCd);
	
	
	// 관리자페이지의 전체 주문리스트
	public List<Order>getOrderAll(PageDto<Order> page);
	
	//관리자페이지 전체주문 카운트
	public int getOrderCount();
	
	// 시설관리자 주문카운트
	public int getOrderByFacilityCount(String facilityCd);
	
	// 주문 관리 검색 카운트
	public int getSearchOrderCount(OrderSearchDto orderSearchDto);
	
	// 주문관리 검색
	public List<Order> getSearchOrderList(Map<String,Object> param);
}
