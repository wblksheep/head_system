package com.haiyin.sprinkler.backend.fileprocessing.service.engine;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;

import java.util.List;
import java.util.Map;

public interface StateRuleEngine {
    boolean canTransition(HeadStatus currentState);
    HeadStatus calculateNextState(HeadStatus currentState, Integer sceneCode);
    Map<Long, HeadStatus> calculateNextStates(List<SprinklerDAO> currentState, Integer sceneCode);
}
