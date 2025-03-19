package com.haiyin.sprinkler.backend.fileprocessing.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

//@AllArgsConstructor
public enum HeadStatus {
    IN_STOCK(0, "库存"),
    IN_USE(1, "使用中"),
    UNDER_MAINTENANCE(2, "维修中"),
    DAMAGED(3, "破损"),
    RMA(4, "RMA流程");

    @Getter
    private final int code;
    @Getter
    private final String desc;

    // 必须显式定义私有构造方法
    private HeadStatus(int code, String description) {
        this.code = code;
        this.desc = description;
    }
}
