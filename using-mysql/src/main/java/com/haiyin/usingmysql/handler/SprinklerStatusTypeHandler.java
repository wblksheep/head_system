package com.haiyin.usingmysql.handler;

import com.haiyin.usingmysql.dto.SprinklerStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.TINYINT)
@MappedTypes(SprinklerStatus.class)
public class SprinklerStatusTypeHandler extends BaseTypeHandler<SprinklerStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SprinklerStatus parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("parameter.getCode()="+parameter.getCode());
        ps.setInt(i, parameter.getCode()); // 存储code到数据库

    }

    @Override
    public SprinklerStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        System.out.println("columnName="+columnName);
        return SprinklerStatus.fromCode(code); // 通过code获取枚举
    }

    @Override
    public SprinklerStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        System.out.println("code="+code);
        System.out.println("SprinklerStatus.fromCode(code)="+SprinklerStatus.fromCode(code));
        return SprinklerStatus.fromCode(code);
    }

    @Override
    public SprinklerStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        System.out.println("code="+code);
        System.out.println("SprinklerStatus.fromCode(code)="+SprinklerStatus.fromCode(code));
        return SprinklerStatus.fromCode(code);
    }

    // 其他 getNullableResult 重载方法类似...
}
