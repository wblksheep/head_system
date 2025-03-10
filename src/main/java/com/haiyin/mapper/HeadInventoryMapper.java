package com.haiyin.mapper;

import com.haiyin.enums.SprinklerStatus;
import com.haiyin.enums.SprinklerType;
import com.haiyin.pojo.HeadInventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface HeadInventoryMapper {
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
                                 @Param("usageDate") LocalDate usageDate,
                                 @Param("headHistory") String headHistory,
                                 @Param("version") int version);

    @Select("select * from tb_head_inventory")
    List<HeadInventory> listAll();

//    // 批量插入
    void insertBatch(@Param("headInventoryList") List<HeadInventory> headInventoryList);

    void insert(HeadInventory inventory);

//    List<HeadInventory> list(@Param("purchaseDate") String purchaseDate, @Param("contractNumber") String contractNumber, @Param("headModel") String headModel, @Param("headSerial") String headSerial, @Param("warehouseDate") String warehouseDate, @Param("usageDate") String usageDate, @Param("user") String user, @Param("usagePurpose") String usagePurpose, @Param("installationSite") String installationSite, @Param("headHistory") String headHistory);
    int batchInsert(List<HeadInventory> sprinklerList);

    HeadInventory findByNo(@NotBlank(message = "喷头编号不能为空") String sprinklerNo);

    List<HeadInventory> list();
//    void batchInsert(List<HeadInventory> initialData);

//    @Select("select * from tb_head_inventory")
//    List<String> list();
}
