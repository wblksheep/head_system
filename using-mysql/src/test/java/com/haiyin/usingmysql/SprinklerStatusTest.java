package com.haiyin.usingmysql;

import com.haiyin.usingmysql.dto.SprinklerStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SprinklerStatusTest {

    @Test
    void fromCode_ValidCodes_ReturnCorrectEnum() {
        // 测试有效 code
        assertEquals(SprinklerStatus.IN_STOCK, SprinklerStatus.fromCode(0));
        assertEquals(SprinklerStatus.IN_USE, SprinklerStatus.fromCode(1));
        assertEquals(SprinklerStatus.UNDER_MAINTENANCE, SprinklerStatus.fromCode(2));
    }

    @Test
    void fromCode_InvalidCode_ThrowException() {
        // 测试无效 code
        assertThrows(IllegalArgumentException.class, () -> SprinklerStatus.fromCode(-1));
        assertThrows(IllegalArgumentException.class, () -> SprinklerStatus.fromCode(3));
    }

    @Test
    void enumProperties_CheckCorrectness() {
        // 验证枚举实例的 code 和 description
        assertEquals(0, SprinklerStatus.IN_STOCK.getCode());
        assertEquals("库存", SprinklerStatus.IN_STOCK.getDescription());

        assertEquals(1, SprinklerStatus.IN_USE.getCode());
        assertEquals("使用中", SprinklerStatus.IN_USE.getDescription());

        assertEquals(2, SprinklerStatus.UNDER_MAINTENANCE.getCode());
        assertEquals("维修中", SprinklerStatus.UNDER_MAINTENANCE.getDescription());
    }
}
