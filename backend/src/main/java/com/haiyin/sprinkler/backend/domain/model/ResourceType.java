package com.haiyin.sprinkler.backend.domain.model;

public enum ResourceType {
    /**
     * 并发控制资源，成本系数 1.0
     */
    CONCURRENCY(1.0, "并发数"),

    /**
     * 内存资源，成本系数 0.7/MB
     */
    MEMORY(0.7, "内存"),

    /**
     * 文件句柄资源，成本系数 0.5/个
     */
    FILE_HANDLE(0.5, "文件句柄");

    private final double costFactor;
    private final String displayName;

    ResourceType(double costFactor, String displayName) {
        this.costFactor = costFactor;
        this.displayName = displayName;
    }

    // 领域方法：计算资源消耗成本
    public double calculateCost(int units) {
        return costFactor * units;
    }

    // 获取资源类型的显示名称
    public String getDisplayName() {
        return displayName;
    }
}
