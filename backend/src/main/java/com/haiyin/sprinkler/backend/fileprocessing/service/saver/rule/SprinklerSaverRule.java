package com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface SprinklerSaverRule {
    /**
     * 解析Excel流为DTO列表
     */
    List<Long> parse(List<SprinklerDAO> daos);

    /**
     * 支持处理的场景类型
     */
    String getSceneType();
}
