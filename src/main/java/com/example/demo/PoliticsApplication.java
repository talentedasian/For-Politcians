package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoliticsApplication {
	
	@Value("${spring.security.oauth2.client.registration.facebook.cliend-id}")
	static String id;

	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
		System.out.println(id);
	}

}
