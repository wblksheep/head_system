package com.haiyin.usingmysql.exception;

// 继承 RuntimeException 表示非受检异常（业务异常常用）
public class SprinklerNotFoundException extends RuntimeException {
    // 序列化版本号（重要！避免序列化兼容性问题）
    private static final long serialVersionUID = 1L;

    // 可扩展字段（例如记录喷头编号）
    private final String sprinklerNo;

    // 基础构造方法
    public SprinklerNotFoundException(String sprinklerNo) {
        super("喷头不存在: " + sprinklerNo);
        this.sprinklerNo = sprinklerNo;
    }

    // 带原因的构造方法（适合异常链）
    public SprinklerNotFoundException(String sprinklerNo, Throwable cause) {
        super("喷头不存在: " + sprinklerNo, cause);
        this.sprinklerNo = sprinklerNo;
    }

    // Getter（可选）
    public String getSprinklerNo() {
        return sprinklerNo;
    }
}
