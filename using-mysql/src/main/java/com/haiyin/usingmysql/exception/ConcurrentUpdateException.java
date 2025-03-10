package com.haiyin.usingmysql.exception;

public class ConcurrentUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // 可扩展字段（例如资源ID、当前版本号）
    private final Long resourceId;
    private final Integer currentVersion;

    // 基础构造方法
    public ConcurrentUpdateException(String message) {
        super(message);
        this.resourceId = null;
        this.currentVersion = null;
    }

    // 带业务参数的构造方法（推荐）
    public ConcurrentUpdateException(Long resourceId, Integer currentVersion) {
        super("资源(ID=" + resourceId + ")版本号已变更，当前版本: " + currentVersion);
        this.resourceId = resourceId;
        this.currentVersion = currentVersion;
    }

    // Getter（可选）
    public Long getResourceId() { return resourceId; }
    public Integer getCurrentVersion() { return currentVersion; }
}
