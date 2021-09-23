package com.example.demo.hateoas;

import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.adapter.out.web.RateLimitProcessor;
import com.example.demo.domain.RateLimitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ProcessorConfigurations {

	@Bean
	@RequestScope
	public HttpServletRequestRatingProcessor httpServletRequestRatingProcessor(RateLimitRepository rateLimitRepository,
																			   HttpServletRequest request) {
		return new HttpServletRequestRatingProcessor(rateLimitRepository, request);
	}
	
	@Bean
	public RateLimitProcessor rateLimitProcessor(RateLimitRepository repository) {
		return new RateLimitProcessor(new RateLimitingService(repository));
	}
	
}
