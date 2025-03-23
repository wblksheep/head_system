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

@Service
public class StateMachineImpl implements StateMachine {
    @Autowired
    private SprinklerRepository sprinklerRepository;
    @Autowired
    private StateRuleEngine stateRuleEngine;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 新增批量状态转移方法
    @Transactional
    @Override
    public void batchRequestTransition(List<Long> daoIds, Integer sceneCode) {
        // 1. 批量查询 DAO
        List<SprinklerDAO> daos = sprinklerRepository.findAllById(daoIds);

        // 2. 批量计算目标状态
        Map<Long, HeadStatus> idToNewStatus = stateRuleEngine.calculateNextStates(daos, sceneCode);

        // 3. 单次 SQL 批量更新
        String sql = "UPDATE tb_sprinklers SET status = ? WHERE id = ? AND status = ?";
        List<Object[]> batchArgs = new ArrayList<>();

        daos.forEach(dao -> {
            HeadStatus newStatus = idToNewStatus.get(dao.getId());
            if (newStatus != null&&newStatus.getCode() == sceneCode) {
                // 参数顺序：newStatus, id, oldStatus
                batchArgs.add(new Object[]{newStatus.getCode(), dao.getId(), dao.getStatus().getCode()});
            }
        });

        // 4. 执行批量更新
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
