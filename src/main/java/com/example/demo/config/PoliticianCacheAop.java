package com.example.demo.config;

import com.example.demo.domain.entities.Politicians;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ehcache.Cache;
import org.ehcache.core.spi.service.StatisticsService;

@Aspect
public class PoliticianCacheAop {

    private final Cache<String, Politicians> cache;
    private final StatisticsService stats;

    public PoliticianCacheAop(Cache<String, Politicians> cache, StatisticsService stats) {
        this.cache = cache;
        this.stats = stats;
    }

    @Around("@annotation(com.example.demo.adapter.in.web.PolCache)")
    public Object doCaching(ProceedingJoinPoint jointP) throws Throwable {
        String polNumber = String.valueOf(jointP.getArgs()[0]);
        System.out.println(stats.getCacheStatistics("research").getCacheHitPercentage() + " cache hit percentage");
        if (cache.containsKey(polNumber))
            return cache.get(polNumber);

        Object response = jointP.proceed();
        cache.put(polNumber, (Politicians) response);

        return response;
    }

}
