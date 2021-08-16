package com.example.demo.hateoas;

import com.example.demo.domain.RateLimitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.adapter.in.service.RateLimitingService;

@Configuration
public class ProcessorConfigurations {

	@Bean
	public RatingProcessor ratingProcessor(RateLimitRepository repository) {
		return new RatingProcessor(repository);
	}
	
	@Bean
	public RateLimitProcessor rateLimitProcessor(RateLimitRepository repository) {
		return new RateLimitProcessor(new RateLimitingService(repository));
	}
	
}
