package com.stupig.kms.common.config;

import com.stupig.kms.common.cache.UserCacheResource;
import com.stupig.kms.common.interceptor.UserCacheInterceptor;
import com.stupig.kms.common.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final UserCacheResource userCacheResource;
    private final SessionService sessionService;

    @Autowired
    public InterceptorConfig(UserCacheResource userCacheResource, SessionService sessionService) {
        this.userCacheResource = userCacheResource;
        this.sessionService = sessionService;
    }

    @Bean
    public UserCacheInterceptor userCacheInterceptor() {
        return new UserCacheInterceptor(userCacheResource, sessionService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userCacheInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/user/signIn",     // U0001 - 로그인
                        "/api/user/signOut",    // U0002 - 로그아웃
                        "/api/user/signUp",     // U0003 - 회원가입 요청
                        "/api/user/idCheck"     // U0004 - 아이디 중복 확인
                );
    }
}
