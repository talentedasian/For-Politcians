package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@SpringBootApplication
@Configuration
public class PoliticsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
	}
	
//	@Bean
//	public OpenAPI openApi() {
//		return new OpenAPI()
//				.components(new Components()
//						.addSecuritySchemes("Politician-Access", new SecurityScheme()
//								.type(Type.HTTP).scheme(null)))
//	}

}
