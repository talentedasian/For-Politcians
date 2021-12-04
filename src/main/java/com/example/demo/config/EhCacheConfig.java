package com.example.demo.config;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfig implements CachingConfigurer {

    net.sf.ehcache.CacheManager ehCacheManager() {
        var researchCacheConfiguration = new CacheConfiguration();
        researchCacheConfiguration.setMemoryStoreEvictionPolicyFromObject(MemoryStoreEvictionPolicy.LFU);
        researchCacheConfiguration.setMaxBytesLocalHeap(100l);
        researchCacheConfiguration.setName("research");
        researchCacheConfiguration.setTimeToLiveSeconds(60);
        researchCacheConfiguration.setLogging(true);

        var config = new net.sf.ehcache.config.Configuration();
        config.addCache(researchCacheConfiguration);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        var cacheManager = new EhCacheCacheManager(ehCacheManager());
        cacheManager.setTransactionAware(true);
        cacheManager.initializeCaches();

        return cacheManager;
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            if (PoliticiansJpaEntity.class.isAssignableFrom(method.getReturnType())) {
//                System.out.println(polNumber + " tangina gago kaba");
                return "FLTT-LFTT-0000";
            }

            return new SimpleKeyGenerator().generate(target, method, params);
        };
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
