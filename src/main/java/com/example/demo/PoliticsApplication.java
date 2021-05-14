package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.jwt.JwtProvider;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@SpringBootApplication
@Configuration
public class PoliticsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
	}
	
	@Bean
	public OpenAPI openApi() {
		String jwt = JwtProvider.createJwtWithNoExpirationDate("test@gmail.com", "test");
		Info info = new Info();
		info.setDescription("Use this jwt `" + jwt + "` in using the \"add-rating\" endpoint from the RatingController");
		var openApi = new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("add-rating", new SecurityScheme()
								.type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		openApi.setInfo(info);
		
		return openApi;
	}

}
