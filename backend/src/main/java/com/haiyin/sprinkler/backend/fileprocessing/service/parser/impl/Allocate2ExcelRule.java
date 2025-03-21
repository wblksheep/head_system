package com.haiyin.sprinkler.backend.fileprocessing.service.parser.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Qualifier("allocate2Rule") // 可选限定符
public class Allocate2ExcelRule implements ExcelParseRule<AllocateDTO> {
    @Override
    public List<AllocateDTO> parse(InputStream stream) throws IOException {
        List<AllocateDTO> allocateList = new ArrayList<>();
        // 使用POI解析为领用DTO
        Workbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // 跳过标题行
            AllocateDTO dto = new AllocateDTO();
            String sprinklerNo = row.getCell(7).getStringCellValue();
            if (sprinklerNo == null || sprinklerNo.isEmpty() || sprinklerNo.equalsIgnoreCase("无")||sprinklerNo.equalsIgnoreCase("无领用")) continue;
            String type = row.getCell(11).getStringCellValue();
            if(type == null || type.isEmpty() || type.equalsIgnoreCase("旧喷头")) continue;
            dto.setHeadSerial(sprinklerNo);
            dto.setUser(row.getCell(6).getStringCellValue());
            dto.setUsagePurpose(row.getCell(8).getStringCellValue());
            dto.setColor(row.getCell(9).getStringCellValue());
            dto.setPosition(row.getCell(10).getStringCellValue());
            allocateList.add(dto);
        }
        return allocateList;
    }

    @Override
    public String getSceneType() {
        return "ALLOCATE2"; // 对应sceneType参数
    }
}
