package com.biagab.gateway.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public final static String ROUTES_CACHE = "routes";
    public final static String ROUTES_KEY = "#id";

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineConfig());
        cacheManager.setAsyncCacheMode(true);
        cacheManager.registerCustomCache(ROUTES_CACHE, Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES).buildAsync());
        return cacheManager;
    }

}
