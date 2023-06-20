package com.arpanm.dispatcherservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class DispatchingFunctionsIntegrationTests {

    private static final Integer PORT = 5672;

    @Container
    private static GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("rabbitmq:3.10-management"))
            .withExposedPorts(PORT);

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getHost);
        registry.add("spring.rabbitmq.port", () -> PORT);
    }

    @Test
    public void contextLoads() {

    }
}
