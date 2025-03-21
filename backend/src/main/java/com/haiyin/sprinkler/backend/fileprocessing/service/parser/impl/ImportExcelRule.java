package com.haiyin.sprinkler.backend.fileprocessing.service.parser.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import com.haiyin.sprinkler.backend.utils.LocalDateParseUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
@Qualifier("importRule")
public class ImportExcelRule implements ExcelParseRule<ImportDTO> {
    @Override
    public List<ImportDTO> parse(InputStream stream) throws IOException {
        // 使用POI解析为导入DTO
        Workbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        List<ImportDTO> list = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // 跳过标题行

            ImportDTO dto = new ImportDTO();
            if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                row.getCell(0).setCellType(CellType.STRING);
            }
            String purchaseContract = row.getCell(0).getStringCellValue();
            Matcher matcher = Pattern.compile("\\d+").matcher(purchaseContract);
            int matcher_start = 0;
            if (matcher.find(matcher_start)) {
                LocalDate purchaseDate = LocalDateParseUtil.parseDate(matcher.group(0));

                // 提取数字
                dto.setPurchaseDate(purchaseDate);
                matcher_start = matcher.end();
            }
            if (matcher.find(matcher_start)) {
                String contractNumber = matcher.group(0);
                dto.setContractNumber(contractNumber);
            }
            dto.setHeadModel(row.getCell(1).getStringCellValue());
            String headSerial = row.getCell(2).getStringCellValue();
            dto.setHeadSerial(headSerial);
            dto.setShippingDate(LocalDateParseUtil.parseDate(row.getCell(3).getStringCellValue()));
            dto.setWarehouseDate(LocalDateParseUtil.parseDate(row.getCell(4).getStringCellValue()));
            dto.setVoltage((float) row.getCell(10).getNumericCellValue());
            dto.setJetsout((int) row.getCell(11).getNumericCellValue());
            if (headSerial != "") {
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public String getSceneType() {
        return "IMPORT";
    }
}
