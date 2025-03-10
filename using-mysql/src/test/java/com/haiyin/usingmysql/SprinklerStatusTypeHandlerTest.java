package com.haiyin.usingmysql;

import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.handler.SprinklerStatusTypeHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SprinklerStatusTypeHandlerTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private CallableStatement callableStatement;

    private final SprinklerStatusTypeHandler typeHandler = new SprinklerStatusTypeHandler();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试参数设置（Java -> 数据库）
    @Test
    void setNonNullParameter_SetsCodeToStatement() throws SQLException {
        typeHandler.setNonNullParameter(preparedStatement, 1, SprinklerStatus.IN_USE, JdbcType.TINYINT);

        // 验证 PreparedStatement.setInt(1, 1) 被调用
        verify(preparedStatement).setInt(1, 1);
    }

    // 测试从列名读取（数据库 -> Java）
    @Test
    void getNullableResult_FromColumnName_ReturnsEnum() throws SQLException {
        when(resultSet.getInt("status")).thenReturn(2);

        SprinklerStatus result = typeHandler.getNullableResult(resultSet, "status");

        assertEquals(SprinklerStatus.UNDER_MAINTENANCE, result);
    }

    // 测试从列索引读取（数据库 -> Java）
    @Test
    void getNullableResult_FromColumnIndex_ReturnsEnum() throws SQLException {
        when(resultSet.getInt(2)).thenReturn(0);

        SprinklerStatus result = typeHandler.getNullableResult(resultSet, 2);

        assertEquals(SprinklerStatus.IN_STOCK, result);
    }

    // 测试从 CallableStatement 读取（数据库 -> Java）
    @Test
    void getNullableResult_FromCallableStatement_ReturnsEnum() throws SQLException {
        when(callableStatement.getInt(3)).thenReturn(1);

        SprinklerStatus result = typeHandler.getNullableResult(callableStatement, 3);

        assertEquals(SprinklerStatus.IN_USE, result);
    }

    // 测试处理 NULL 值
    @Test
    void getNullableResult_NullValue_ReturnsNull() throws SQLException {
        when(resultSet.getInt("status")).thenReturn(0); // 正常情况
        when(resultSet.wasNull()).thenReturn(true);     // 标记为 NULL

        SprinklerStatus result = typeHandler.getNullableResult(resultSet, "status");

        assertNull(result);
    }
}
