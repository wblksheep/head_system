package com.haiyin.sprinkler.backend.fileprocessing.service.engine.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.service.engine.StateRuleEngine;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StateRuleEngineImpl implements StateRuleEngine {

    private final Map<HeadStatus, Set<HeadStatus>> transitionRules = new EnumMap<>(HeadStatus.class);

    public StateRuleEngineImpl() {
        // 初始化状态转移规则
        transitionRules.put(HeadStatus.IN_STOCK, Set.of(HeadStatus.IN_USE));
        transitionRules.put(HeadStatus.IN_USE, Set.of(HeadStatus.UNDER_MAINTENANCE));
        transitionRules.put(HeadStatus.UNDER_MAINTENANCE, Set.of(HeadStatus.DAMAGED, HeadStatus.RMA, HeadStatus.IN_STOCK));
//        transitionRules.put(HeadStatus.DAMAGED, Set.of(HeadStatus.RMA));
//        transitionRules.put(HeadStatus.RMA, Set.of(HeadStatus.IN_STOCK));
    }

    @Override
    public boolean canTransition(HeadStatus currentState) {
        // 检查当前状态是否有允许的转移目标
        return transitionRules.containsKey(currentState) && !transitionRules.get(currentState).isEmpty();
    }

//    @Override
//    public HeadStatus calculateNextState(HeadStatus currentState, Integer sceneCode) {
//        // 简单逻辑：返回第一个允许的状态
//        Set<HeadStatus> allowedStates = transitionRules.get(currentState);
//        if (allowedStates == null || allowedStates.isEmpty()) {
//            throw new IllegalStateException("No allowed transitions from state: " + currentState);
//        }
//        return allowedStates.iterator().next();
//    }

    @Override
    public HeadStatus calculateNextState(HeadStatus currentState, Integer sceneCode) {
        Set<HeadStatus> allowedStates = transitionRules.get(currentState);
        if (allowedStates == null || allowedStates.isEmpty()) {
            throw new IllegalStateException("No allowed transitions from state: " + currentState);
        }

        // 根据 sceneCode 选择状态
        if (sceneCode != null) {
            for (HeadStatus state : allowedStates) {
                if (isValidForScene(state, sceneCode)) {
                    return state;
                }
            }
        }

        // 如果没有匹配的场景，返回第一个允许的状态
        return allowedStates.iterator().next();
    }

    private boolean isValidForScene(HeadStatus state, Integer sceneCode) {
        // 实现场景码与状态的匹配逻辑
        // 例如，某些状态只能在特定场景下使用
        return state.getCode() == sceneCode; // 默认返回 true，需根据业务逻辑调整
    }


    // 批量计算下一状态（返回 Map<ID, 新状态>）
    @Override
    public Map<Long, HeadStatus> calculateNextStates(List<SprinklerDAO> daos, Integer sceneCode) {
        return daos.stream()
                .filter(dao -> canTransition(dao.getStatus()))
                .collect(Collectors.toMap(
                        SprinklerDAO::getId,
                        dao -> calculateNextState(dao.getStatus(), sceneCode)
                ));
    }

//    @Override
//    public Map<Long, HeadStatus> calculateNextStates(List<SprinklerDAO> currentStates) {
//        return currentStates.stream()
//                .filter(this::canTransition)
//                .collect(Collectors.toMap(
//                        Function.identity(),
//                        this::calculateNextState
//                ));
//    }

}
