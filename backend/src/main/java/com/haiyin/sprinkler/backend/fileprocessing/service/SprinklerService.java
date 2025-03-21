package com.haiyin.sprinkler.backend.fileprocessing.service;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;

import java.util.List;

public interface SprinklerService {
    void batchSave(List<SprinklerDAO> daos);
    List<Long> batchUpsert(List<SprinklerDAO> daos, String sceneType);
}
