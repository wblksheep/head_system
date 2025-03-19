package com.haiyin.sprinkler.backend.domain.factory;

import com.haiyin.sprinkler.backend.domain.model.BatchContext;
import com.haiyin.sprinkler.backend.domain.model.SceneType;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchContextFactory {

    // 使用工厂模式创建上下文
    public static BatchContext createContext(String sceneType) {
        validateSceneType(sceneType); // 参数校验

        return new BatchContext(
                generateBatchId(),
                parseSceneType(sceneType),
                LocalDateTime.now(),
                new ConcurrentHashMap<>(),
                new CopyOnWriteArrayList<>(),
                new AtomicInteger(0)
        );
    }

    private static void validateSceneType(String sceneType) {
        if (!SceneType.isValid(sceneType)) {
            throw new IllegalArgumentException("无效的场景类型: " + sceneType);
        }
    }

    private static String generateBatchId() {
        return "BATCH-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    private static SceneType parseSceneType(String sceneType) {
        return SceneType.valueOf(sceneType.toUpperCase());
    }
}
