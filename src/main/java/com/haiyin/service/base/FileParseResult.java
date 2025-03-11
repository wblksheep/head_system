package com.haiyin.service.base;

import com.haiyin.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FileParseResult {
    // 原始记录集合（Excel/TXT解析后的原始数据）
    private List<RawRecord> rawRecords = new ArrayList<>();

    // 转换后的DTO集合（校验前的中间态）
    private List<BaseDTO> convertedRecords = new ArrayList<>();

    // 解析错误信息（格式错误的行）
    private List<ParseError> parseErrors = new ArrayList<>();

    // 文件类型标识
    private FileType fileType;

    // 元数据（如文件名、解析时间等）
    private Map<String, Object> metadata = new HashMap<>();

    // 枚举定义
    public enum FileType {
        ALLOCATION_EXCEL,
        MAINTENANCE_EXCEL,
        INVENTORY_TXT
    }

    // 嵌套类定义
    public static class RawRecord {
        private int rowNumber;
        private Map<String, String> fields;

        public RawRecord(int rowNumber, Map<String, String> fields) {
            this.rowNumber = rowNumber;
            this.fields = fields;
        }
    }

    @AllArgsConstructor
    public static class ParseError {
        private int rowNumber;
        private String errorMessage;
        private String rawContent;

    }
}
