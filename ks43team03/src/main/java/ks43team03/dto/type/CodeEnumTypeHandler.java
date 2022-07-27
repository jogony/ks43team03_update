package ks43team03.dto.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> implements TypeHandler<CodeEnum> {

	private Class <E> type;
	 
    public CodeEnumTypeHandler(Class <E> type) {
        this.type = type;
    }
	
	@Override
	public void setParameter(PreparedStatement ps, int i, CodeEnum parameter, JdbcType jdbcType) throws SQLException {
		log.info("setParameter code data: {}", parameter.getCode());
		ps.setString(i, parameter.getCode());
	}

	@Override
	public CodeEnum getResult(ResultSet rs, String columnName) throws SQLException {
		String code = rs.getString(columnName);
		log.info("getResult code data: {}", code);
		return getCodeEnum(code);
	}

	@Override
	public CodeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);
		log.info("getResult code data: {}", code);
		return getCodeEnum(code);
	}

	@Override
	public CodeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
		 String code = cs.getString(columnIndex);
		 log.info("getResult code data: {}", code);
	     return getCodeEnum(code);
	}
	
	private CodeEnum getCodeEnum(String code) {
        return EnumSet.allOf(type)
                .stream()
                .filter(data -> data.getCode().equals(code))
                .findFirst()
                .orElseGet(null);
    }

}
