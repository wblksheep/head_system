package com.haiyin.usingmysql;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.dto.SprinklerType;
import com.haiyin.usingmysql.mapper.SprinklerMapper;
import com.haiyin.usingmysql.pojo.Sprinkler;
import com.haiyin.usingmysql.service.SprinklerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprinklerServiceSingleInsertTest {
    private String testSprinklerNo;
    @Autowired
    private SprinklerService sprinklerService;

    @Autowired
    private SprinklerMapper sprinklerMapper;

    @Test
    void testAllocateSprinkler() {
        // 生成唯一 sprinklerNo
//        testSprinklerNo = "TEST-" + UUID.randomUUID().toString().substring(0, 8);
        testSprinklerNo = "285199-19";
//        insertTestData(testSprinklerNo, SprinklerStatus.IN_STOCK, 0);

        // 构造请求
        SprinklerAllocationDTO dto = new SprinklerAllocationDTO();
        dto.setSprinklerNo(testSprinklerNo);
        dto.setOwner("卢伟");
        dto.setMachine("大昌祥扫描机");
        dto.setColor("C");
        dto.setPosition("1");
        dto.setType(SprinklerType.OLD);

        // 执行领用
        sprinklerService.allocateSprinkler(dto);

        // 验证结果
        Sprinkler updated = sprinklerMapper.findByNo(testSprinklerNo);
        assertThat(updated.getStatus()).isEqualTo(SprinklerStatus.IN_USE);
        assertThat(updated.getVersion()).isEqualTo(1);
    }

    private void insertTestData(String sprinklerNo, SprinklerStatus status, int version) {
        // 创建初始状态的 Sprinkler 对象
        Sprinkler sprinkler = new Sprinkler();
        sprinkler.setSprinklerNo(sprinklerNo);
        sprinkler.setStatus(status);
        sprinkler.setVersion(version);
        sprinkler.setType(SprinklerType.NEW);  // 根据测试需求设置默认类型

        // 可选：设置其他必要字段（根据数据库约束）
        // sprinkler.setPosition("default_position");

        // 调用 MyBatis Mapper 插入数据
        sprinklerMapper.insert(sprinkler);
    }


}
