package com.haiyin.sprinkler.backend.fileprocessing.repository;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface SprinklerRepository extends JpaRepository<SprinklerDAO, Long> {
    //    @Transactional
    default List<SprinklerDAO> batchSave(List<SprinklerDAO> daos) {
        return saveAll(daos);
    }

//    List<SprinklerDAO> batchUpsert(List<SprinklerDAO> daos);

    @Modifying
    @Query("UPDATE SprinklerDAO s SET s.status = :newStatus WHERE s.id = :id AND s.status = :expectedStatus")
    int updateState(@Param("id") Long id, @Param("newStatus") HeadStatus newStatus, @Param("expectedStatus") HeadStatus expectedStatus);

    @Modifying
    @Query("UPDATE SprinklerDAO s SET s.status = :newStatus " +
            "WHERE s.id IN :ids AND s.status = :oldStatus")
    int batchUpdateState(
            @Param("ids") List<Long> ids,
            @Param("newStatus") HeadStatus newStatus,
            @Param("oldStatus") HeadStatus oldStatus
    );
}
