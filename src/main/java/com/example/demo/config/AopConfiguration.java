package com.example.demo.config;

import com.example.demo.domain.entities.Politicians;
import com.example.demo.logger.PoliticiansLogger;
import org.ehcache.Cache;
import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {

	@Bean
	public PoliticiansLogger polLogger() {
		return new PoliticiansLogger();
	}

	@Bean
	public PoliticianCacheAop politicianCacheAop(Cache<String, Politicians> cache, StatisticsService stats) {
		return new PoliticianCacheAop(cache, stats);
	}

}
