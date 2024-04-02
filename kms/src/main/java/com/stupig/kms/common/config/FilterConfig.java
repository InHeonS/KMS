package com.stupig.kms.common.config;

import com.stupig.kms.common.filter.BodyLoggingRequestFilter;
import com.stupig.kms.common.filter.ContentCachingRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class FilterConfig {

    /* Header, Client Info, QueryString Logging 처리 */
    @Bean
    public FilterRegistrationBean<CommonsRequestLoggingFilter> commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);

        FilterRegistrationBean<CommonsRequestLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    /* Request Readable 변환 및 Response Caching 처리 */
    @Bean
    public FilterRegistrationBean<ContentCachingRequestFilter> contentCachingRequestFilter() {
        FilterRegistrationBean<ContentCachingRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ContentCachingRequestFilter());
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    /* (ContentCachingRequestFilter 필요) Request, Response Logging */
    @Bean
    public FilterRegistrationBean<BodyLoggingRequestFilter> bodyLoggingRequestFilter() {
        FilterRegistrationBean<BodyLoggingRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new BodyLoggingRequestFilter());
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }

}
