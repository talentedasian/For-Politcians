package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PoliticsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
	}
	
	@Bean
	public RestTemplate template() {
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(
		        new FormHttpMessageConverter(),
		        new OAuth2AccessTokenResponseHttpMessageConverter()));

		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		
		return restTemplate;
	}

}
