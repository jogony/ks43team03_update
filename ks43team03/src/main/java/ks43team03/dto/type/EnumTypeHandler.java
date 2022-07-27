package ks43team03.dto.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E>{

	private Class<E> type;

	public EnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("형식 인수는 null입니다.");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String code = rs.getString(columnName);
		return rs.wasNull() ? null : codeOf(code);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);
		return rs.wasNull() ? null : codeOf(code);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String code = cs.getString(columnIndex);
		return cs.wasNull() ? null : codeOf(code);
	}
	
	private E codeOf(String code){
		try {
			return EnumUtils.getCodeEnum(type, code);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
		}
	}
	
	
}
