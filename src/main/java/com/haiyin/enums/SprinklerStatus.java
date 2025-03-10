package com.haiyin.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

// Lombok自动生成getter
public enum SprinklerStatus {   // 喷头状态枚举
    // 枚举实例定义
    IN_STOCK(0, "库存"),        // 第一个状态
    IN_USE(1, "使用中"),         // 第二个状态
    UNDER_MAINTENANCE(2, "维修中");

    // 状态码字段（不可变）
    private final int code;
    // 状态描述字段
    private final String description;

    // 状态码映射表（静态初始化）
    private static final Map<Integer, SprinklerStatus> CODE_MAP =
            Arrays.stream(values())  // 遍历所有枚举实例
                    .collect(Collectors.toMap(
                            SprinklerStatus::getCode, // 提取code作为key
                            Function.identity()      // 枚举实例本身作为value
                    ));

    // 通过code获取枚举实例的方法
    public static SprinklerStatus fromCode(int code) {
        return Optional.ofNullable(CODE_MAP.get(code))   // 安全获取
                .orElseThrow(() -> new IllegalArgumentException("无效状态码: " + code));
    }

    // 必须显式定义私有构造方法
    private SprinklerStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
