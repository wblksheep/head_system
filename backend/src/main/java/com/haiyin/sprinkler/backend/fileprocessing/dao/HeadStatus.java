package com.haiyin.sprinkler.backend.fileprocessing.dao;

public enum HeadStatus {
    IN_STOCK(0, "库存"),
    IN_USE(1, "使用中"),
    UNDER_MAINTENANCE(2, "维修中"),
    DAMAGED(3, "破损"),
    RMA(4, "RMA流程");

    private final int code;
    private final String desc;

    private HeadStatus(int code, String description) {
        this.code = code;
        this.desc = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
