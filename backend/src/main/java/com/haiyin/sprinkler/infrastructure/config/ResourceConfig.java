package com.haiyin.sprinkler.infrastructure.config;

import com.haiyin.sprinkler.backend.infrastructure.resource.manager.SemaphoreResourceManager;
import com.haiyin.sprinkler.backend.infrastructure.resource.spi.ResourceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {
    @Value("${resource.max-concurrency:5}")
    private int maxConcurrency;

    @Bean
    public ResourceManager resourceManager() {
        return new SemaphoreResourceManager(maxConcurrency);
    }

//    @Bean
//    public ProcessQueue processQueue(
//            @Value("${queue.capacity:100}") int capacity) {
//        return new ProcessQueue(capacity);
//    }
}
