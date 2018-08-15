package org.tinywind.webtemplate.util.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.jooq.EnumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author tinywind
 * @since 2017-05-14
 */
public class JooqEnumTypeHandler<E extends Enum<E> & EnumType> implements TypeHandler<E> {
	private static final Logger logger = LoggerFactory.getLogger(JooqEnumTypeHandler.class);

	private Class<E> type;

	@SuppressWarnings("unchecked")
	public JooqEnumTypeHandler() {
		Type genericSuperclass = getClass().getGenericSuperclass();
		if (!(genericSuperclass instanceof ParameterizedType))
			return;

		Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
		if (actualTypeArguments.length < 1)
			return;

		type = (Class<E>) actualTypeArguments[0];
	}

	public JooqEnumTypeHandler(Class<E> type) {
		this.type = type;
	}

	public Class<E> getType() {
		return type;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getLiteral());
	}

	@Override
	public E getResult(ResultSet rs, String columnName) throws SQLException {
		String code = rs.getString(columnName);
		return valueOf(code);
	}

	@Override
	public E getResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);
		return valueOf(code);
	}

	@Override
	public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String code = cs.getString(columnIndex);
		return valueOf(code);
	}

	public E valueOf(String code) {
		for (E value : type.getEnumConstants())
			if (Objects.equals(value.getLiteral(), code))
				return value;

		throw new TypeException("Can't make enum object " + type);
	}

	public E valueOf(char c) {
		return valueOf(String.valueOf(c));
	}
}
