package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SpringDoc {

	@Bean
	public OpenAPI openApi() {
		Info info = new Info();
		info.setVersion("1.0.0");
		info.setDescription("""
				Login with `http://localhost:8080/oauth2/authorization/facebook` first to get a
				 Json Web Token to be used in the `add-rating` endpoint from the
				`Ratings Controller`
				""");
		
		
		var openApi = new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("add-rating", new SecurityScheme()
								.type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
		
		openApi.setInfo(info);
		
		return openApi;
	}
}
