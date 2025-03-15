package com.haiyin.service.interfaces.impl;

import com.haiyin.dto.SprinklerAllocationDTO;
import com.haiyin.enums.SprinklerType;
import com.haiyin.service.interfaces.FileParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("allocationFileParser")
public class AllocationFileParser implements FileParser {
    private class Info<T> {
        List<T> suc;
        List<T> fail;

        public Info() {
            suc = new ArrayList<>();
            fail = new ArrayList<>();
        }
    }

    private class ExcelRecord {
        String color;
        int ph;
        String sValue;
        SprinklerAllocationDTO dto;

        public ExcelRecord(String color, int ph, String sValue, SprinklerAllocationDTO dto) {
            this.color = color;
            this.ph = ph;
            this.sValue = sValue;
            this.dto = dto;
        }

    }

    private List<TxtRecord> parseTxtFile(MultipartFile file) throws Exception {
        List<TxtRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 提取Color值
                String[] parts = line.split("Color_");
                parts = parts[1].split(" PH_");
                String color = parts[0];
                parts = parts[1].split(":");
                int ph = Integer.parseInt(parts[0].trim());
                String rest = "";
                if (parts.length > 1) {
                    rest = parts[1];
                }
                // 提取序列号部分
                parts = rest.split("/s");
                if (parts != null && parts.length > 1) {
                    // 提取序列号
                    parts = parts[1].split("/");
                    String sValue = parts[0];
                    records.add(new TxtRecord(color, ph, sValue));
//                    if (sSegment.startsWith("s")) {
//                        String sValue = sSegment.substring(1);
//                        records.add(new TxtRecord(color, ph, sValue));
//                    }
                }
            }
        }

        return records;
    }
    private Info<SprinklerAllocationDTO> getDiffInfo(MultipartFile excelFile, MultipartFile txtFile) {

        try {
            List<TxtRecord> txtRecords = parseTxtFile(txtFile);
            List<ExcelRecord> excelRecords = parseExcelFile(excelFile);
            // 3. 匹配记录
            Info<SprinklerAllocationDTO> info = matchAndPrintResults(txtRecords, excelRecords);

            return info;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Info<SprinklerAllocationDTO> matchAndPrintResults(List<TxtRecord> txtRecords, List<ExcelRecord> excelRecords) {
//         创建复合键的哈希映射（sValue + "|" + k）
        Info info = new Info();
        Map<String, TxtRecord> txtMap = new HashMap<>();
        for (TxtRecord txt : txtRecords) {
            String compositeKey = txt.color + "|" + txt.ph;
            txtMap.put(compositeKey, txt);
        }

        // 单层循环查找
        for (ExcelRecord excelRecord : excelRecords) {
            String lookupKey = excelRecord.color + "|" + excelRecord.ph;
            TxtRecord matched = txtMap.get(lookupKey);
            boolean ok = false;
            if (matched != null) {
                ok = matched.sValue.equals(excelRecord.sValue);
            }
            if (ok) {
                System.out.printf("匹配成功: Excel记录[color=%s, ph=%d, s=%s] - TXT记录[color=%s, ph=%d, s=%s]%n",
                        excelRecord.color, excelRecord.ph, excelRecord.sValue, matched.color, matched.ph, matched.sValue);
                info.suc.add(excelRecord.dto);
            } else {
                System.out.printf("匹配不成功: Excel记录[color=%s, ph=%d, s=%s]%n",
                        excelRecord.color, excelRecord.ph, excelRecord.sValue);
                info.fail.add(excelRecord.dto);
            }
        }
        return info;
    }

    private List<ExcelRecord> parseExcelFile(MultipartFile file) throws Exception {
        List<ExcelRecord> records = new ArrayList<>();
        try (InputStream fis = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行
                SprinklerAllocationDTO dto = new SprinklerAllocationDTO();
                String sprinklerNo = row.getCell(2).getStringCellValue();
                if (sprinklerNo == null || sprinklerNo.isEmpty()) continue;
                dto.setSprinklerNo(sprinklerNo);
                dto.setOwner(row.getCell(6).getStringCellValue());
                dto.setMachine(row.getCell(7).getStringCellValue());
                Cell kCell = row.getCell(8);
                if (kCell == null) continue;
                String kStr = kCell.getStringCellValue();
                String color_position = row.getCell(8).getStringCellValue();
                Matcher matcher = Pattern.compile("(\\d+)").matcher(color_position);
                if (matcher.find()) {
                    String color = color_position.substring(0, matcher.start());
                    String position = matcher.group();
                    dto.setColor(color);
                    dto.setPosition(position);
                }
                dto.setType(SprinklerType.NEW);
                dto.setUsageDate(LocalDate.now());
                dto.setHistory(row.getCell(9).getStringCellValue());

                records.add(new ExcelRecord(dto.getColor(), Integer.parseInt(dto.getPosition()), dto.getSprinklerNo(), dto));
            }
        }
        return records;
    }

    private class TxtRecord {
        String color;
        int ph;
        String sValue;

        public TxtRecord(String color, int ph, String sValue) {
            this.color = color;
            this.ph = ph;
            this.sValue = sValue;
        }

        public String getPosition(String position) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(position);
            if (matcher.find()) {
                int phNumber = Integer.parseInt(matcher.group(1));
            }
            return sValue;
        }
    }

    @Override
    public List<SprinklerAllocationDTO> parseFiles(MultipartFile... files) {
        MultipartFile excelFile = files[0];
        MultipartFile txtFile = files[1];
        Info<SprinklerAllocationDTO> info = getDiffInfo( excelFile, txtFile);
        return null;
    }
}
