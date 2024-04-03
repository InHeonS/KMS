package com.stupig.kms.common.filter;

import com.stupig.kms.common.wrapper.ReadableHttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

public class ContentCachingRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ReadableHttpServletRequestWrapper wrappingRequest = request instanceof ReadableHttpServletRequestWrapper ? (ReadableHttpServletRequestWrapper) request : new ReadableHttpServletRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = response instanceof ContentCachingResponseWrapper ? (ContentCachingResponseWrapper) response : new ContentCachingResponseWrapper(response);
        filterChain.doFilter(wrappingRequest, wrappingResponse);
        System.out.println("실행 위치 확인용 ContentCachingRequestFilter");
        wrappingResponse.copyBodyToResponse();
    }
}
