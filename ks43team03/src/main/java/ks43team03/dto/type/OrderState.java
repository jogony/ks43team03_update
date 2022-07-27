package ks43team03.dto.type;

import java.util.Arrays;

import org.apache.ibatis.type.MappedTypes;




public enum OrderState implements CodeEnum{

	ORDER("주문 진행중"),COMPLETE("주문완료"), FAIL("주문실패"), CANCEL("주문취소");
	
	private String code;
	
	OrderState(String code) {
		this.code = code;
	}
	
	
	
	public static OrderState getEnumByCode(String code) {
		OrderState[] value = values();
		return Arrays.stream(value)
				.filter(e -> e.getCode().equalsIgnoreCase(code))
				.findFirst()
				.orElse(null);
	}

	@MappedTypes(OrderState.class)
    public static class TypeHandler extends CodeEnumTypeHandler<OrderState> {
        public TypeHandler() {
            super(OrderState.class);
        }
    }


	@Override
	public String getCode() {
		return code;
	}
	


	
}
