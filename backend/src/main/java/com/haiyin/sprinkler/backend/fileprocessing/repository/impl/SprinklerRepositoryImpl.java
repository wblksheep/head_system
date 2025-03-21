//package com.haiyin.sprinkler.backend.fileprocessing.repository.impl;
//
//import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
//import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
//import com.haiyin.sprinkler.backend.fileprocessing.repository.SprinklerRepository;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.data.repository.query.Param;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Repository
//public class SprinklerRepositoryImpl implements SprinklerRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<SprinklerDAO> batchUpsert(List<SprinklerDAO> daos) {
//        String sql = "INSERT INTO tb_sprinklers (id, head_serial, status, usage_date, user, usage_purpose, head_history, color, position, update_time) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
//                "ON DUPLICATE KEY UPDATE " +
//                "head_serial = VALUES(head_serial), status = VALUES(status), usage_date=VALUES(usage_date), user=VALUES(user), usage_purpose=VALUES(usage_purpose), head_history=VALUES(head_history), color=VALUES(color), position=VALUES(position), update_time=VALUES(update_time)";
//
//        List<Object[]> batchArgs = daos.stream()
//                .map(dao -> new Object[]{
//                        dao.getId(),
//                        dao.getHeadSerial(),
//                        dao.getStatus().getCode(),
//                        dao.getUsageDate(),
//                        dao.getUser(),
//                        dao.getUsagePurpose(),
//                        dao.getHeadHistory(),
//                        dao.getColor(),
//                        dao.getPosition(),
//                        LocalDateTime.now()
//                })
//                .collect(Collectors.toList());
//
//        jdbcTemplate.batchUpdate(sql, batchArgs);
//
//        // 2. 清除 Hibernate 缓存，确保后续查询获取最新数据
//        entityManager.flush();
//        entityManager.clear();
//
//        // 3. 返回更新后的实体
//        return findAllById(
//                daos.stream().map(SprinklerDAO::getId).collect(Collectors.toList())
//        );
//    }
//}
