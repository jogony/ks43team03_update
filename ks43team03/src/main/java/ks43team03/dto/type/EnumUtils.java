package ks43team03.dto.type;

import java.util.EnumSet;

public class EnumUtils {

	public static <E extends Enum<E>& CodeEnum> E getCodeEnum(Class<E> type, String code) {
		return EnumSet.allOf(type)
				  .stream()
				  .filter(t -> t.getCode().equals(code))
				  .findFirst()
				  .orElse(null);
	}
}
