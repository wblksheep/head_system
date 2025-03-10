package com.haiyin.usingmysql.mapper;

import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.dto.SprinklerType;
import com.haiyin.usingmysql.pojo.HeadInventory;
import com.haiyin.usingmysql.pojo.Sprinkler;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SprinklerMapper {
    @Update({
            "UPDATE tb_head_inventory SET status = #{newStatus.code},user = #{owner},usage_purpose = #{machine},usage_date = #{usageDate},color = #{color},position = #{position},type = #{type},head_history=#{headHistory},version = version + 1 WHERE head_serial = #{sprinklerNo} AND status = #{oldStatus.code} AND version = #{version}"
    })
    int allocateSprinkler(@Param("sprinklerNo") String sprinklerNo,
                          @Param("oldStatus") SprinklerStatus oldStatus,
                          @Param("newStatus") SprinklerStatus newStatus,
                          @Param("owner") String owner,
                          @Param("machine") String machine,
                          @Param("color") String color,
                          @Param("position") String position,
                          @Param("type") SprinklerType type,
                          @Param("usageDate")LocalDate usageDate,
                          @Param("headHistory") String headHistory,
                          @Param("version") int version);

    Sprinkler findByNo(String sprinklerNo);

    int insert(Sprinkler sprinkler);



    @Delete("DELETE from tb_sprinklers where sprinkler_no = #{sprinklerNo}")
    void deleteBySprinklerNo(String testSprinklerNo);

    @Transactional
    int batchInsert(List<HeadInventory> sprinklerList);
}
