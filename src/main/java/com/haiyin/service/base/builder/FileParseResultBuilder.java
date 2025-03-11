package com.haiyin.service.base.builder;

import com.haiyin.dto.BaseDTO;
import com.haiyin.service.base.FileParseResult;
import com.haiyin.service.base.FileParseResult.RawRecord;
import com.haiyin.service.base.FileParseResult.ParseError;
import com.haiyin.service.base.FileParseResult.FileType;
import java.time.LocalDateTime;
import java.util.Map;

public class FileParseResultBuilder {
    private final FileParseResult result = new FileParseResult();

    public FileParseResultBuilder withFileType(FileType type) {
        result.setFileType(type);
        return this;
    }

    public FileParseResultBuilder addRawRecord(int rowNum, Map<String, String> fields) {
        result.getRawRecords().add(new RawRecord(rowNum, fields));
        return this;
    }

    public FileParseResultBuilder addConvertedRecord(BaseDTO dto) {
        result.getConvertedRecords().add(dto);
        return this;
    }

    public FileParseResultBuilder addParseError(int rowNum, String error, String raw) {
        result.getParseErrors().add(new ParseError(rowNum, error, raw));
        return this;
    }

    public FileParseResult build() {
        // 添加自动生成的元数据
        result.getMetadata().put("parseTime", LocalDateTime.now());
        return result;
    }
}
