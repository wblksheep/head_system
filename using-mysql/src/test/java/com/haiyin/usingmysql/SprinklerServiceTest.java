package com.haiyin.usingmysql;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.dto.SprinklerType;
import com.haiyin.usingmysql.mapper.SprinklerMapper;
import com.haiyin.usingmysql.pojo.Sprinkler;
import com.haiyin.usingmysql.service.SprinklerService;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SprinklerServiceTest {
    private static SqlSessionFactory sqlSessionFactory;
    // 存储测试生成的 sprinklerNo
    private String testSprinklerNo;
    @Autowired
    private SprinklerService sprinklerService;

    @Autowired
    private SprinklerMapper sprinklerMapper;






    @BeforeAll
    static void initDatabase() throws Exception {
        // 1. 配置数据源（H2 内存数据库）
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        // 2. 构建 SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                new org.apache.ibatis.session.Configuration() {{
                    addMappers("com.haiyin.usingmysql.mapper"); // 指定 Mapper 包路径
                    setEnvironment(new Environment(
                            "test",
                            new JdbcTransactionFactory(),
                            dataSource
                    ));
                }}
        );

        // 3. 建表
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.getConnection().createStatement().execute(
                    "CREATE TABLE tb_sprinklers (" +
                            "  id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                            "  sprinkler_no VARCHAR(20) UNIQUE NOT NULL," +
                            "  status TINYINT NOT NULL," +
                            "  owner VARCHAR(50)," +
                            "  machine VARCHAR(50)," +
                            "  color CHAR(1)," +
                            "  position VARCHAR(10)," +
                            "  type ENUM('NEW', 'OLD') NOT NULL DEFAULT 'OLD'," +
                            "  version INT NOT NULL DEFAULT 0," +
                            "  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                            ")");
        }
    }
    @AfterEach
    void tearDown() {
        if (testSprinklerNo != null) {
            sprinklerMapper.deleteBySprinklerNo(testSprinklerNo); // 需在 Mapper 中实现删除方法
        }
    }

    @Test
    void testAllocateSprinkler() {
        // 生成唯一 sprinklerNo
        testSprinklerNo = "TEST-" + UUID.randomUUID().toString().substring(0, 8);
        insertTestData(testSprinklerNo, SprinklerStatus.IN_STOCK, 0);

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
        sprinkler.setType(SprinklerType.OLD);  // 根据测试需求设置默认类型

        // 可选：设置其他必要字段（根据数据库约束）
        // sprinkler.setPosition("default_position");

        // 调用 MyBatis Mapper 插入数据
        sprinklerMapper.insert(sprinkler);
    }
}
