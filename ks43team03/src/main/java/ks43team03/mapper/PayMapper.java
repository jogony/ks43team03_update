package ks43team03.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Pay;

@Mapper
public interface PayMapper {
	
	// 결제 등록
	public int addPay(Pay payment);
	// 결제한 카드 내역 등록	
	public int addPayCardInfo(Pay pay);
	
	// 결제할 가상계좌 등록 
	public int addPayVirtualAccount(Pay pay);
	
	public Optional<Pay> getPayByOrderCd(String orderCd);
	
	public int modifyPay(Pay pay);
}
