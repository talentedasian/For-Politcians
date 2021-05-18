package com.example.demo.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.jwt.JwtProvider;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SpringDoc {

	@Bean
	public OpenAPI openApi() {
		String jwt = JwtProvider.createJwtWithNoExpirationDate("test@gmail.com", "test");
		Info info = new Info();
		info.setVersion("1.0.0");
		info.setDescription("Use this jwt `" + jwt + "` in using the \"add-rating\" endpoint from the RatingController");
		var openApi = new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("add-rating", new SecurityScheme()
								.type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		openApi.setInfo(info);
		
		return openApi;
	}
}
