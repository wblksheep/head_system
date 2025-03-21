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
@Qualifier("maintainSaverRule")
public class MaintainSaverRule extends AbstractSaverTemplate{
    
    @Override
    protected String getInsertSQL() {
        return "INSERT INTO tb_sprinklers (id, head_serial, user, usage_purpose, color, position, usage_date, head_history, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "head_serial = VALUES(head_serial), user=VALUES(user), usage_purpose=VALUES(usage_purpose), color=VALUES(color), position=VALUES(position), usage_date=VALUES(usage_date), head_history=VALUES(head_history), update_time=VALUES(update_time)";
    }


    @Override
    protected MapSqlParameterSource createSelectParams(List<SprinklerDAO> daos) {
        return new MapSqlParameterSource().addValue("serials", daos.stream()
                .map(SprinklerDAO::getHeadSerial)
                .collect(Collectors.toList()));
    }

    @Override
    protected Object[] mapDAOToParams(SprinklerDAO dao) {
        return new Object[]{
                dao.getId(),
                dao.getHeadSerial(),
                dao.getUser(),
                dao.getUsagePurpose(),
                dao.getColor(),
                dao.getPosition(),
                dao.getUsageDate(),
                dao.getHeadHistory(),
                LocalDateTime.now()
        };
    }

    @Override
    public String getSceneType() {
        return "MAINTAIN";
    }
}
