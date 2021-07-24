package com.example.demo.baseClasses;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class BaseClassTestsThatUsesDatabase {

	public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres"))
		.withDatabaseName("test-container")
		.withPassword("test")
		.withUsername("test")
		.withStartupTimeoutSeconds(120)
		.withReuse(true);
	
	static {
		container.start();
	}
	
	@DynamicPropertySource
	static void setup(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getUsername);
		registry.add("spring.datasource.username", container::getPassword);
	}
	
}
