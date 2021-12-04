package com.example.demo.config;

import com.example.demo.domain.entities.Politicians;
import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.internal.statistics.DefaultStatisticsService;
import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhCacheConfig{

    @Bean
    StatisticsService defaultStatsService() {
        return new DefaultStatisticsService();
    }

    @Bean
    Cache<String, Politicians> defaultCache() {
        var cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preconfigure", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Politicians.class,
                        ResourcePoolsBuilder.heap(100)))
                .using(defaultStatsService())
                .build();
        cacheManager.init();

        Cache<String, Politicians> defaultCache = cacheManager.createCache("research",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Politicians.class,
                            ResourcePoolsBuilder.heap(100))
                .build());

        return defaultCache;
    }

}
