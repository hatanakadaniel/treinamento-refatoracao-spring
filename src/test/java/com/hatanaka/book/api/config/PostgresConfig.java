package com.hatanaka.book.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Slf4j
public class PostgresConfig {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("bookdbtest")
            .withUsername("bookusertest")
            .withPassword("123mudar");

    static {
        postgresContainer.start();
        postgresContainer.waitingFor(Wait.forHealthcheck());
        log.info("c=PostgresConfig, jdbcUrl={}", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
    }
}
