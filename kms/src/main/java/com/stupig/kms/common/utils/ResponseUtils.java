package com.stupig.kms.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ResponseUtils {

    public static void responseError(HttpServletResponse response, String msg) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(msg);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> void responseError(HttpServletResponse response, T t) {
        try {
            responseError(response, new ObjectMapper().writeValueAsString(t));
        } catch (JsonProcessingException jpe) {
            log.error(jpe.getMessage(), jpe);
        }
    }
}
