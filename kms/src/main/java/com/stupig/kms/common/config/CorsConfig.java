package com.stupig.kms.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")				// url 패턴
                .allowedOriginPatterns("*")		// 허용 url
                .allowCredentials(true);		// 쿠키 허용 여부
    }
}
