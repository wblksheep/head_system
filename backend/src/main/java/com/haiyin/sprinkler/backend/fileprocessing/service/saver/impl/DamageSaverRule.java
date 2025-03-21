package com.haiyin.sprinkler.backend.fileprocessing.service.saver.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.AbstractSaverTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("damageSaverRule")
public class DamageSaverRule extends AbstractSaverTemplate{
    
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
        return "DAMAGE";
    }
}
