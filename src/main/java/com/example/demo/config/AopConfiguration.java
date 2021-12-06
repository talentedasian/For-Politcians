package com.example.demo.config;

import com.example.demo.logger.PoliticiansLogger;
import com.example.demo.domain.entities.Politicians;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.demo.logger.PoliticiansLogger;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Bean
	public PoliticiansLogger polLogger() {
		return new PoliticiansLogger();
	}

	@Bean
	public PoliticianCacheAop politicianCacheAop(Cache<String, Politicians> cache) {
		return new PoliticianCacheAop(cache);
	}
}
