package com.hatanaka.book.api.config;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class RedisConfig {

    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:6").withExposedPorts(6379);

    static {
        redisContainer.start();
        redisContainer.waitingFor(Wait.forHealthcheck());
        System.setProperty("spring.redis.host", redisContainer.getContainerIpAddress());
        System.setProperty("spring.redis.port", redisContainer.getMappedPort(6379).toString());
    }
}
