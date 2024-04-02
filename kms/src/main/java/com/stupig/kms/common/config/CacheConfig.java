package com.stupig.kms.common.config;

import com.stupig.kms.common.cache.UserCacheResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public UserCacheResource userCacheResource() {
        return new UserCacheResource();
    }
}
