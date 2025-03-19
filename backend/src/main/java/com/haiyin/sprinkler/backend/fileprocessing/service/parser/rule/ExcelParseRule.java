package com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ExcelParseRule<T> {
    /**
     * 解析Excel流为DTO列表
     */
    List<T> parse(InputStream stream) throws IOException;

    /**
     * 支持处理的场景类型
     */
    String getSceneType();
}
