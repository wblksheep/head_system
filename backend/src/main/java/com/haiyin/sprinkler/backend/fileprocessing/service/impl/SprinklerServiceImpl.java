package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.repository.SprinklerRepository;
import com.haiyin.sprinkler.backend.fileprocessing.service.SprinklerService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SprinklerServiceImpl implements SprinklerService {
    @Autowired
    private SprinklerRepository sprinklerRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public void batchSave(List<SprinklerDAO> daos) {
        sprinklerRepository.batchSave(daos);
    }

    @Transactional
    @Override
    public List<Long> batchUpsert(List<SprinklerDAO> daos, String sceneType) {

        String sql = "INSERT INTO tb_sprinklers (id, head_serial, usage_date, user, usage_purpose, head_history, color, position, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "head_serial = VALUES(head_serial), usage_date=VALUES(usage_date), user=VALUES(user), usage_purpose=VALUES(usage_purpose), head_history=VALUES(head_history), color=VALUES(color), position=VALUES(position), update_time=VALUES(update_time)";

        List<Object[]> batchArgs = daos.stream()
                .map(dao -> new Object[]{
                        dao.getId(),
                        dao.getHeadSerial(),
                        dao.getUsageDate(),
                        dao.getUser(),
                        dao.getUsagePurpose(),
                        dao.getHeadHistory(),
                        dao.getColor(),
                        dao.getPosition(),
                        LocalDateTime.now()
                })
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(sql, batchArgs);

//        // 2. 清除 Hibernate 缓存，确保后续查询获取最新数据
//        daos.forEach(entityManager::persist);
        entityManager.flush();
        entityManager.clear();

//        // 根据业务键二次查询确保ID准确
//        List<String> headSerials = daos.stream()
//                .map(SprinklerDAO::getHeadSerial)
//                .toList();
        // 获取所有 head_serial
        List<String> headSerials = daos.stream()
                .map(SprinklerDAO::getHeadSerial)
                .collect(Collectors.toList());

//        List<Map<String, Object>> results1 = daos.stream()
//                .map(dao -> {
//                    String sqll = "SELECT id FROM tb_sprinklers WHERE head_serial = ?";
//                    return jdbcTemplate.queryForList(sqll, dao.getHeadSerial());
//                })
//                .flatMap(List::stream)
//                .collect(Collectors.toList());

        sql = "SELECT id FROM tb_sprinklers WHERE head_serial IN (:serials)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("serials", headSerials);

        List<Map<String, Object>> results2 = namedParameterJdbcTemplate.queryForList(
                sql,
                parameters
        );

        // 3. 返回更新后的实体
//        return sprinklerRepository.findByHeadSerials(headSerials);
        List<Long> ids = results2.stream().map(map -> (Long)(map.get("id"))).toList();
        return ids;
    }
}
