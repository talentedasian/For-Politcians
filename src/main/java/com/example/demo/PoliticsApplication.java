package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class PoliticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticsApplication.class, args);
	}

}
