package com.haiyin.sprinkler.backend.fileprocessing.service.saver.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.repository.SprinklerRepository;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.AbstractSaverTemplate;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.SprinklerSaverRule;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("allocateSaverRule")
public class AllocateSaverRule extends AbstractSaverTemplate{

    @Override
    protected String getInsertSQL() {
        return "INSERT INTO tb_sprinklers (id, head_serial, usage_date, user, usage_purpose, head_history, color, position, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "head_serial = VALUES(head_serial), usage_date=VALUES(usage_date), user=VALUES(user), usage_purpose=VALUES(usage_purpose), head_history=VALUES(head_history), color=VALUES(color), position=VALUES(position), update_time=VALUES(update_time)";
    }

    @Override
    protected Object[] mapDAOToParams(SprinklerDAO dao) {
        return new Object[]{
                dao.getId(),
                dao.getHeadSerial(),
                dao.getUsageDate(),
                dao.getUser(),
                dao.getUsagePurpose(),
                dao.getHeadHistory(),
                dao.getColor(),
                dao.getPosition(),
                LocalDateTime.now()
        };
    }


    @Override
    public String getSceneType() {
        return "ALLOCATE";
    }
}
