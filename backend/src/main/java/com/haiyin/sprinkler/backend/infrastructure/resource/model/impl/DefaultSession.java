package com.haiyin.sprinkler.backend.infrastructure.resource.model.impl;

import com.haiyin.sprinkler.backend.domain.model.ResourceType;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.Session;

import java.time.Instant;
import java.util.UUID;

public record DefaultSession(
        UUID sessionId,
        Instant createTime,
        ResourceType type, // 使用枚举状态替代布尔值
        int weight,
        SessionStatus status
) implements Session {

    @Override
    public boolean isValid() {
        return Session.super.isValid();
    }

    @Override
    public Session transition(SessionStatus newStatus) {
        return new DefaultSession(
                sessionId,
                createTime,
                type,
                weight,
                newStatus
        );
    }
}
