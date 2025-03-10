package com.haiyin.pojo;

import com.haiyin.enums.SprinklerStatus;
import com.haiyin.enums.SprinklerType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HeadInventory {

    private Integer id; // 自增主键

    private LocalDate shippingDate;//发货日期

    private LocalDate purchaseDate; // 购入日期

    private String contractNumber; // 合同编号

    private String headModel; // 喷头型号

    private String headSerial; // 喷头序列号，唯一且不能为空

    private LocalDate warehouseDate; // 入仓日期

    private Float voltage;//电压

    private Integer jetsout;//jetsout

    private LocalDate usageDate; // 领用日期

    private String user; // 领用人

    private String usagePurpose; // 领用用途

    private String headHistory; // 喷头历史

    private String color;
    private String position;

    private SprinklerStatus status;

    private SprinklerType type = SprinklerType.NEW;    // 默认值 OLD
    private Integer version = 0;     // 乐观锁默认值

    private LocalDateTime updateTime;

}
