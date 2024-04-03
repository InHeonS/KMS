package com.stupig.kms.common.filter;

import com.stupig.kms.common.utils.DateUtils;
import com.stupig.kms.common.wrapper.ReadableHttpServletRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class BodyLoggingRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request instanceof ReadableHttpServletRequestWrapper) {
            ReadableHttpServletRequestWrapper cachingRequest = (ReadableHttpServletRequestWrapper) request;
            System.out.println("실행 위치 확인용 BodyLoggingRequestFilter");
            String requestHttpMethod = request.getMethod();
            String requestPath = request.getRequestURI();
            int requestLength = cachingRequest.getContentLength();
            String requestBody = new String(cachingRequest.getContentAsByteArray());

            try {
                if (cachingRequest.getContentAsByteArray().length > 5000) {
                    log.debug("Request Payload [{}][{}][{}][{}...(cut)]", requestHttpMethod, requestPath, requestLength, requestBody.substring(0, 5000));
                }
                else {
                    log.debug("Request Payload [{}][{}][{}][{}]", requestHttpMethod, requestPath, requestLength, requestBody);
                }
            } catch (Exception e) {
                log.error("### REQUEST BODY LOGGING ERROR [{}][{}][{}]", requestHttpMethod, requestPath, e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

            int responseSize = cachingResponse.getContentSize();
            String responseBody = new String(cachingResponse.getContentAsByteArray());
            String dateTime = DateUtils.getNowDate("yyyy-MM-dd HH:mm:ss");

            try {
                if (responseBody.length() > 5000) {
                    log.debug("Response Payload [{}][{}][{}...(cut)]", dateTime, responseSize, responseBody.substring(0, 5000));
                }
                else {
                    log.debug("Response Payload [{}][{}][{}]", dateTime, responseSize, responseBody);
                }
            } catch (Exception e) {
                log.error("### RESPONSE BODY LOGGING ERROR [{}][{}]", dateTime, e.getMessage());
            }
        }
    }
}
