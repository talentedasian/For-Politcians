package com.example.demo;

import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.UserRateLimitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRateLimitServiceConfiguration {
    @Bean
    public UserRateLimitService rateLimitService(RateLimitRepository rateLimitRepository) {
        return new DefaultRateLimitDomainService(rateLimitRepository);
    }
}
