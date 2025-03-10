package com.haiyin.usingmysql.utils;

import org.apache.poi.hssf.record.chart.TextRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {

    public static void main(String[] args) throws Exception {
        // 1. 解析txt文件
//        List<TxtRecord> txtRecords = parseTxtFile("input.txt");
        List<TxtRecord> txtRecords = parseTxtFile("C:\\Users\\Design-10\\Desktop\\Code\\spring-code\\big-event\\using-mysql\\input.txt");
        // 2. 解析Excel文件
//        List<ExcelRecord> excelRecords = parseExcelFile("data.xlsx");
        List<ExcelRecord> excelRecords = parseExcelFile("C:\\Users\\Design-10\\Desktop\\Code\\spring-code\\big-event\\using-mysql\\data.xlsx");

        // 3. 匹配记录
        matchAndPrintResults(txtRecords, excelRecords);
    }

    static List<TxtRecord> parseTxtFile(String filePath) throws Exception {
        List<TxtRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    static List<ExcelRecord> parseExcelFile(String filePath) throws Exception {
        List<ExcelRecord> records = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行

                // 提取第三列（索引2）
                Cell sCell = row.getCell(2);
                if (sCell == null) continue;
                String sValue = sCell.getStringCellValue();

                // 提取K列（索引7）
                Cell kCell = row.getCell(8);
                if (kCell == null) continue;
                String kStr = kCell.getStringCellValue();

                // 提取数字
                Matcher matcher = Pattern.compile("\\d+").matcher(kStr);
                if (matcher.find()) {
                    String color = kStr.substring(0, matcher.start());
                    int ph = Integer.parseInt(matcher.group());
                    records.add(new ExcelRecord(color, ph, sValue));
                }
            }
        }
        return records;
    }

    static void matchAndPrintResults(List<TxtRecord> txtRecords, List<ExcelRecord> excelRecords) {
//         创建复合键的哈希映射（sValue + "|" + k）
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
                        excelRecord.color,excelRecord.ph, excelRecord.sValue, matched.color, matched.ph, matched.sValue);
            } else {
                System.out.printf("匹配不成功: Excel记录[color=%s, ph=%d, s=%s]%n",
                        excelRecord.color, excelRecord.ph, excelRecord.sValue);
            }
        }
    }

    static class TxtRecord {
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

    static class ExcelRecord {
        String color;
        int ph;
        String sValue;

        public ExcelRecord(String color, int ph, String sValue) {
            this.color = color;
            this.ph = ph;
            this.sValue = sValue;
        }

    }
}
