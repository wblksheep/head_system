package com.haiyin.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelChineseWidth {
    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("中文测试");

        // 设置中文字体
        Font chineseFont = workbook.createFont();
        chineseFont.setFontName("微软雅黑");
        CellStyle style = workbook.createCellStyle();
        style.setFont(chineseFont);

        // 写入数据
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("自动调整列宽测试：这是一段较长的中文文本，用于验证列宽自适应。");

        // 自动调整列宽
        sheet.autoSizeColumn(0);

//        // 手动增加宽度（可选）
//        int currentWidth = sheet.getColumnWidth(0);
//        sheet.setColumnWidth(0, (int) (currentWidth * 1.2));

        // 保存文件
        FileOutputStream fos = new FileOutputStream("ChineseWidthDemo.xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
