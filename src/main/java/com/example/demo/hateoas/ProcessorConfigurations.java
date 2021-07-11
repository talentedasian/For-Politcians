package com.example.demo.hateoas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.RateLimitingService;

@Configuration
public class ProcessorConfigurations {

	@Bean
	public RatingProcessor ratingProcessor(RateLimitingService service) {
		return new RatingProcessor(service);
	}
	
	@Bean
	public RateLimitProcessor rateLimitProcessor(RateLimitingService service) {
		return new RateLimitProcessor(service);
	}
	
}
