package com.haiyin.usingmysql.utils;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.dto.SprinklerImportDTO;
import com.haiyin.usingmysql.dto.SprinklerType;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelReader {
    public static List<SprinklerImportDTO> readImportExcel(String filePath) {
        List<SprinklerImportDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            CellStyle textStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@"));
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行

                SprinklerImportDTO dto = new SprinklerImportDTO();
                if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    row.getCell(0).setCellType(CellType.STRING);
                }
                String purchaseContract = row.getCell(0).getStringCellValue();
                Matcher matcher = Pattern.compile("\\d+").matcher(purchaseContract);
//\s*（\s*\d+\s*）
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
//                dto.setOwner(row.getCell(6).getStringCellValue());
//                dto.setMachine(row.getCell(7).getStringCellValue());
//                String color_position = row.getCell(8).getStringCellValue();
//                matcher = Pattern.compile("(\\d+)").matcher(color_position);
//                if (matcher.find()) {
//                    String color = color_position.substring(0, matcher.start());
//                    String position = matcher.group();
//                    // 提取数字
//                    dto.setColor(color);
//                    dto.setPosition(position);
//                }
                dto.setType(SprinklerType.NEW);

                if (headSerial != "") {
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("解析 Excel 失败", e);
        }
        return list;
    }

    public static List<SprinklerAllocationDTO> readAllocateExcel(String filePath) {
        List<SprinklerAllocationDTO> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            CellStyle textStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@"));
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行

                SprinklerAllocationDTO dto = new SprinklerAllocationDTO();
//\s*（\s*\d+\s*）
                String sprinklerNo = row.getCell(2).getStringCellValue();
                if(sprinklerNo == ""){
                    continue;
                }
                dto.setSprinklerNo(sprinklerNo);
                dto.setOwner(row.getCell(6).getStringCellValue());
                dto.setMachine(row.getCell(7).getStringCellValue());
                String color_position = row.getCell(8).getStringCellValue();
                Matcher matcher = Pattern.compile("(\\d+)").matcher(color_position);
                if (matcher.find()) {
                    String color = color_position.substring(0, matcher.start());
                    String position = matcher.group();
                    // 提取数字
                    dto.setColor(color);
                    dto.setPosition(position);
                }
                dto.setType(SprinklerType.NEW);
                dto.setUsageDate(LocalDate.now());
                dto.setHistory(row.getCell(9).getStringCellValue());

                list.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("解析 Excel 失败", e);
        }
        return list;
    }
}
