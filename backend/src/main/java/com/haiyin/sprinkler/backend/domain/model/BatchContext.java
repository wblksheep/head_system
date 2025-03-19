package com.haiyin.sprinkler.backend.domain.model;

import com.haiyin.sprinkler.backend.infrastructure.resource.ResourceHandle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public record BatchContext(
        String batchId,                // 批次唯一标识
        SceneType sceneType,           // 业务场景枚举
        LocalDateTime createTime,      // 创建时间
        ConcurrentMap<String, FileProcessingState> fileStates, // 文件处理状态映射
        List<ResourceHandle> allocatedResources, // 资源句柄列表
        AtomicInteger processedCount   // 已处理文件计数器
) {
}
