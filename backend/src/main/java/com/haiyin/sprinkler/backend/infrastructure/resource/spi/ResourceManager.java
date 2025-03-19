package com.haiyin.sprinkler.backend.infrastructure.resource.spi;

import com.haiyin.sprinkler.backend.infrastructure.exception.ResourceException;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.Session;

public interface ResourceManager {
    Session acquire() throws ResourceException;
    void release(Session session);
}
