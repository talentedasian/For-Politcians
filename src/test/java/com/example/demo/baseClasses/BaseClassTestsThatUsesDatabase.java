package com.example.demo.baseClasses;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/*
 * Classes that use an underlying database such as 
 * @DatabaseTest and @SpringBootTest must extend this class.
 */
public abstract class BaseClassTestsThatUsesDatabase {

	static final PostgreSQLContainer<?> container;
	
	static {
		container = new PostgreSQLContainer<>(DockerImageName.parse("postgres")).withReuse(true);
		container.start();
	}
	
	@DynamicPropertySource
	static void setup(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getUsername);
		registry.add("spring.datasource.username", container::getPassword);
	}
	
}
