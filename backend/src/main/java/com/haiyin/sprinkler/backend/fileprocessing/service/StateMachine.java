package com.haiyin.sprinkler.backend.fileprocessing.service;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;

import java.util.List;

public interface StateMachine {
    void batchRequestTransition(List<Long> daoIds, Integer sceneType);
}
