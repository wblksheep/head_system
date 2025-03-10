package com.haiyin.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TransferCellTypeUtil {

    public static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // 空单元格返回空字符串
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期类型转换为指定格式的字符串
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    // 数值类型转换为字符串
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // 布尔值转换为字符串
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue()); // 数值结果
                } catch (IllegalStateException e) {
                    return cell.getStringCellValue(); // 字符串结果
                }
            case BLANK:
                return ""; // 空单元格
            default:
                return ""; // 其他类型返回空字符串
        }
    }
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestSheet");
        Row row = sheet.createRow(0);

        // 测试字符串单元格
        Cell stringCell = row.createCell(0, CellType.STRING);
        stringCell.setCellValue("Test String");
        System.out.println("String Cell: " + getCellValueAsString(stringCell));

        // 测试数值单元格
        Cell numericCell = row.createCell(1, CellType.NUMERIC);
        numericCell.setCellValue(12345.678);
        System.out.println("Numeric Cell: " + getCellValueAsString(numericCell));

        // 测试日期单元格
        Cell dateCell = row.createCell(2, CellType.NUMERIC);
        dateCell.setCellValue(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
        System.out.println("Date Cell: " + getCellValueAsString(dateCell));

        // 测试布尔单元格
        Cell booleanCell = row.createCell(3, CellType.BOOLEAN);
        booleanCell.setCellValue(true);
        System.out.println("Boolean Cell: " + getCellValueAsString(booleanCell));

        // 测试公式单元格（数值结果）
        Cell formulaCellNumeric = row.createCell(4, CellType.FORMULA);
        formulaCellNumeric.setCellFormula("1+1");
        System.out.println("Formula Numeric Cell: " + getCellValueAsString(formulaCellNumeric));

        // 测试公式单元格（字符串结果）
        Cell formulaCellString = row.createCell(5, CellType.FORMULA);
        formulaCellString.setCellFormula("\"Formula Result\"");
        System.out.println("Formula String Cell: " + getCellValueAsString(formulaCellString));

        // 测试空白单元格
        Cell blankCell = row.createCell(6, CellType.BLANK);
        System.out.println("Blank Cell: " + getCellValueAsString(blankCell));

        // 测试空单元格
        Cell nullCell = null;
        System.out.println("Null Cell: " + getCellValueAsString(nullCell));
    }
}
