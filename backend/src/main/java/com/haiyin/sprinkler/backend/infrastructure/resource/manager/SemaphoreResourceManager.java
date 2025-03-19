package com.haiyin.sprinkler.backend.infrastructure.resource.manager;

import com.haiyin.sprinkler.backend.domain.model.ResourceType;
import com.haiyin.sprinkler.backend.infrastructure.exception.ResourceException;
import com.haiyin.sprinkler.backend.infrastructure.exception.errorcode.ErrorCode;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.Session;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.impl.DefaultSession;
import com.haiyin.sprinkler.backend.infrastructure.resource.spi.ResourceManager;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class SemaphoreResourceManager implements ResourceManager {
    private final Semaphore semaphore;

    public SemaphoreResourceManager(int maxConcurrency) {
        this.semaphore = new Semaphore(maxConcurrency);
    }

    @Override
    public Session acquire() throws ResourceException {
        try {
            semaphore.acquire();
            return Session.create(ResourceType.CONCURRENCY, 0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResourceException(ErrorCode.RESOURCE_EXHAUSTED, e.toString());
        }
    }

    @Override
    public void release(Session session) {
        semaphore.release();
//        ((DefaultSession) session).invalidate();
    }
}
