package com.hatanaka.book.api.config;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class SQSConfig {

    private static final LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
            .withServices(LocalStackContainer.Service.S3);

    static {
        localStackContainer.start();
        localStackContainer.waitingFor(Wait.forHealthcheck());
        System.out.println(localStackContainer.getEndpointOverride(LocalStackContainer.Service.S3));
        System.out.println(localStackContainer.getAccessKey());
        System.out.println(localStackContainer.getRegion());
        System.out.println(localStackContainer.getSecretKey());
    }
}
