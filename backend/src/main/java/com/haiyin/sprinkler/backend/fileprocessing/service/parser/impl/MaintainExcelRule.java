package com.haiyin.sprinkler.backend.fileprocessing.service.parser.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
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
@Qualifier("maintainRule") // 可选限定符
public class MaintainExcelRule implements ExcelParseRule<MaintainDTO> {
    @Override
    public List<MaintainDTO> parse(InputStream stream) throws IOException {
        List<MaintainDTO> allocateList = new ArrayList<>();
        // 使用POI解析为领用DTO
        Workbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // 跳过标题行
            String sprinklerNo = row.getCell(12).getStringCellValue();
            if (sprinklerNo == null || sprinklerNo.isEmpty() || sprinklerNo.equalsIgnoreCase("无")|| sprinklerNo.equalsIgnoreCase("无返仓")) continue;
            MaintainDTO dto = new MaintainDTO();
            dto.setHeadSerial(sprinklerNo);
            String isOrNo = row.getCell(13).getStringCellValue();
            dto.setUser(row.getCell(6).getStringCellValue());
            if(isOrNo.equals("是")){
                dto.setUsagePurpose(row.getCell(8).getStringCellValue());
                dto.setColor(row.getCell(9).getStringCellValue());
                dto.setPosition(row.getCell(10).getStringCellValue());
            }else{
                dto.setUsagePurpose(row.getCell(14).getStringCellValue());
                dto.setColor(row.getCell(15).getStringCellValue());
                dto.setPosition(row.getCell(16).getStringCellValue());
            }
            allocateList.add(dto);
        }
        return allocateList;
    }

    @Override
    public String getSceneType() {
        return "MAINTAIN"; // 对应sceneType参数
    }
}
