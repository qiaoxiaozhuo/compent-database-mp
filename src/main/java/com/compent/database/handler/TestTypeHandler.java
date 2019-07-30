package com.compent.database.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qiaoxiaozhuo 2019/04/02
 */
public class TestTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, "TestTypeHandler set {" + parameter + "}");
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        return "TestTypeHandler(rs columnName) get {" + string + "}";
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        return "TestTypeHandler(rs columnIndex) get {" + string + "}";
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        return "TestTypeHandler(cs columnIndex) get {" + string + "}";
    }
}
