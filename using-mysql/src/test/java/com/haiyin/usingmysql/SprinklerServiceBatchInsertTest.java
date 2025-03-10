package com.haiyin.usingmysql;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.dto.SprinklerImportDTO;
import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.mapper.SprinklerMapper;
import com.haiyin.usingmysql.pojo.HeadInventory;
import com.haiyin.usingmysql.pojo.Sprinkler;
import com.haiyin.usingmysql.service.SprinklerService;
import com.haiyin.usingmysql.utils.ExcelReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SprinklerServiceBatchInsertTest {

    @Autowired
    SprinklerService sprinklerService;

    @Autowired
    SprinklerMapper sprinklerMapper;

    @Test
    void testBatchImportSprinklers() {
        // 1. 从 Excel 读取数据
        String excelPath = "src/test/resources/batch_sprinklers.xlsx";
        List<SprinklerImportDTO> dtos = ExcelReader.readImportExcel(excelPath);

        // 2. 批量插入初始数据（状态为 IN_STOCK）
        List<HeadInventory> initialData = dtos.stream()
                .map(dto -> {
                    HeadInventory s = new HeadInventory();
                    s.setShippingDate(dto.getShippingDate());
                    s.setPurchaseDate(dto.getPurchaseDate());
                    s.setContractNumber(dto.getContractNumber());
                    s.setHeadModel(dto.getHeadModel());
                    s.setHeadSerial(dto.getHeadSerial());
                    s.setWarehouseDate(dto.getWarehouseDate());
                    s.setVoltage(dto.getVoltage());
                    s.setJetsout(dto.getJetsout());
                    s.setStatus(SprinklerStatus.IN_STOCK);
                    s.setVersion(0);
                    s.setType(dto.getType());
                    return s;
                })
                .collect(Collectors.toList());
//        sprinklerService.batchImportSprinklers(initialData);
        sprinklerMapper.batchInsert(initialData);

//        throw new RuntimeException("运行异常");

//        // 3. 执行批量分配
//        sprinklerService.batchAllocateSprinklers(dtos);
//
//        // 4. 验证所有记录状态和版本
//        for (SprinklerAllocationDTO dto : dtos) {
//            Sprinkler updated = sprinklerMapper.findByNo(dto.getSprinklerNo());
//            assertThat(updated.getStatus()).isEqualTo(SprinklerStatus.IN_USE);
//            assertThat(updated.getVersion()).isEqualTo(1);
//        }
    }

    @Test
    void testBatchAllocateSprinklers() {
        testBatchImportSprinklers();
        // 1. 从 Excel 读取数据
        String excelPath = "src/test/resources/batch_sprinklers.xlsx";
        List<SprinklerAllocationDTO> dtos = ExcelReader.readAllocateExcel(excelPath);

        // 3. 执行批量分配
        sprinklerService.batchAllocateSprinklers(dtos);

        // 4. 验证所有记录状态和版本
        for (SprinklerAllocationDTO dto : dtos) {
            Sprinkler updated = sprinklerMapper.findByNo(dto.getSprinklerNo());
            assertThat(updated.getStatus()).isEqualTo(SprinklerStatus.IN_USE);
            assertThat(updated.getVersion()).isEqualTo(1);
        }
    }
}
