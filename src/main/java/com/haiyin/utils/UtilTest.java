package com.haiyin.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class UtilTest {
    public static void main(String[] args) {
        String dateString = "12-JUL-22".trim(); // 去除空格

        // 定义日期格式，并指定区域设置为英文
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()  // 忽略大小写
                .appendPattern("dd-MMM-yy")
                .toFormatter(Locale.ENGLISH);

        try {
            // 解析日期字符串
            LocalDate date = LocalDate.parse(dateString, formatter);
            System.out.println("解析后的日期: " + date);
        } catch (Exception e) {
            System.out.println("日期解析失败: " + e.getMessage());
        }
    }
}
