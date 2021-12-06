package com.example.demo.config;

import com.example.demo.domain.entities.Politicians;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.github.benmanes.caffeine.cache.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Optional;

@Aspect
public class PoliticianCacheAop {

    private final Cache<String, Politicians> cache;

    public PoliticianCacheAop(Cache<String, Politicians> cache) {
        this.cache = cache;
    }

    @Around("@annotation(com.example.demo.adapter.in.web.PolCache)")
    public Object doCaching(ProceedingJoinPoint jointP) throws Throwable {
        String polNumber = String.valueOf(jointP.getArgs()[0]);
        Politicians cacheValue = cache.getIfPresent(polNumber);
        boolean doesCacheContain = (cacheValue != null);
        if (doesCacheContain) {
            return Optional.of(cacheValue);
        }

        Object returnObject = jointP.proceed();
        Politicians methodResponse = (((Optional<? extends Politicians>) returnObject))
                .orElseThrow(() -> PoliticianNotFoundException.withPolNumber(polNumber));
        cache.put(polNumber, methodResponse);

        return returnObject;
    }

    @AfterReturning(pointcut = "execution (* com.example.demo.adapter.out.repository.PoliticianJpaAdapterRepository.update(..))",
                    returning = "politician")
    public void updateCacheValue(Politicians politician) throws Throwable {
        cache.put(politician.retrievePoliticianNumber(), politician);
    }

}
