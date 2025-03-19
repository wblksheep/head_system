package com.haiyin.sprinkler.backend.fileprocessing.service.parser.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Qualifier("allocateRule") // 可选限定符
public class AllocateExcelRule implements ExcelParseRule<AllocateDTO> {
    @Override
    public List<AllocateDTO> parse(InputStream stream) throws IOException {
        // 使用POI解析为领用DTO
        Workbook workbook = new XSSFWorkbook(stream);
        Sheet sheet = workbook.getSheetAt(0);

        // 具体解析逻辑...
//        return allocateList;
        return List.of();
    }

    @Override
    public String getSceneType() {
        return "ALLOCATE"; // 对应sceneType参数
    }
}
