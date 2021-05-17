package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demo.jwt.JwtProvider;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@SpringBootApplication
public class PoliticsApplication {
	
	public static void main(String[] args) {
		LocalDateTime dateTime = LocalDateTime.now().minusMinutes(30L);
		Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		System.out.println(JwtProvider.createJwtWithDynamicExpirationDate("james@gmail.com", "james", date));
		SpringApplication.run(PoliticsApplication.class, args);
	}
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
	
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
