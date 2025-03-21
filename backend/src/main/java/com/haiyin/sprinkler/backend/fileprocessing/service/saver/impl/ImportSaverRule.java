package com.haiyin.sprinkler.backend.fileprocessing.service.saver.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.repository.SprinklerRepository;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.SprinklerSaverRule;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("importSaverRule")
public class ImportSaverRule implements SprinklerSaverRule {

    @Autowired
    private SprinklerRepository sprinklerRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<Long> parse(List<SprinklerDAO> daos) {

        sprinklerRepository.batchSave(daos);

        entityManager.flush();
        entityManager.clear();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("serials", daos.stream()
                .map(SprinklerDAO::getHeadSerial)
                .collect(Collectors.toList()));

        List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(
                "SELECT id FROM tb_sprinklers WHERE head_serial IN (:serials)",
                parameters
        );

        return results.stream().map(map -> (Long)(map.get("id"))).toList();
    }

    @Override
    public String getSceneType() {
        return "IMPORT";
    }
}
