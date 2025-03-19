package com.haiyin.sprinkler.backend.infrastructure.resource.model;

import com.haiyin.sprinkler.backend.domain.model.ResourceType;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.impl.DefaultSession;
import org.springframework.web.bind.support.SessionStatus;

import java.time.Instant;
import java.util.UUID;

public interface Session {
    // ================= 核心属性访问 =================
    UUID sessionId();
    Instant createTime();
    ResourceType type();
    int weight();
    SessionStatus status();

    // ================= 状态机操作 =================
    default boolean isValid() {
        return status() == SessionStatus.ACTIVE &&
                createTime().plusMillis(30000).isAfter(Instant.now());
    }

    Session transition(SessionStatus newStatus);

    // ================= 工厂方法 =================
    static Session create(ResourceType type, int weight) {
        return new DefaultSession(
                generateSessionId(),
                Instant.now(),
                type,
                weight,
                SessionStatus.ACTIVE
        );
    }

    private static UUID generateSessionId() {
        // 直接使用UUID标准生成方法
        return UUID.randomUUID();
    }

    // ================= 嵌套枚举类型 =================
    enum SessionStatus {
        ACTIVE, RELEASED, TIMEOUT
    }

}
