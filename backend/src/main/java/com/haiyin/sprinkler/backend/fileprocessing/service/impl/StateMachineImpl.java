package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.repository.SprinklerRepository;
import com.haiyin.sprinkler.backend.fileprocessing.service.StateMachine;
import com.haiyin.sprinkler.backend.fileprocessing.service.engine.StateRuleEngine;
import com.haiyin.sprinkler.backend.fileprocessing.service.exception.StateConflictException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateMachineImpl implements StateMachine {
    @Autowired
    private SprinklerRepository sprinklerRepository;
    @Autowired
    private StateRuleEngine stateRuleEngine;

    @Override
    public void requestTransition(Long daoId, HeadStatus expectedState) {
        SprinklerDAO dao = sprinklerRepository.findById(Long.valueOf(daoId))
                .orElseThrow(() -> new EntityNotFoundException("SprinklerDAO not found: " + daoId));
        if (dao.getStatus() == expectedState && stateRuleEngine.canTransition(dao.getStatus())) {
            HeadStatus newState = stateRuleEngine.calculateNextState(dao.getStatus());
            int updated = sprinklerRepository.updateState(daoId, newState, expectedState);
            if (updated == 0) {
                throw new StateConflictException("状态冲突，当前状态已变更");
            }
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 新增批量状态转移方法
    @Transactional
    @Override
    public void batchRequestTransition(List<Long> daoIds) {
        // 1. 批量查询 DAO
        List<SprinklerDAO> daos = sprinklerRepository.findAllById(daoIds);

        // 2. 批量计算目标状态
        Map<Long, HeadStatus> idToNewStatus = stateRuleEngine.calculateNextStates(daos);

        // 3. 单次 SQL 批量更新
        String sql = "UPDATE tb_sprinklers SET status = ? WHERE id = ? AND status = ?";
        List<Object[]> batchArgs = new ArrayList<>();

        daos.forEach(dao -> {
            HeadStatus newStatus = idToNewStatus.get(dao.getId());
            if (newStatus != null) {
                // 参数顺序：newStatus, id, oldStatus
                batchArgs.add(new Object[]{newStatus.getCode(), dao.getId(), dao.getStatus().getCode()});
            }
        });

        // 4. 执行批量更新
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
