package com.stupig.kms.common.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stupig.kms.common.annotations.QueryStringToJson;
import com.stupig.kms.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class QueryStringToJsonResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;
    
    @Autowired
    public QueryStringToJsonResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        System.out.println("실행 위치 확인용 resolver");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(QueryStringToJson.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return objectMapper.readValue(
                JsonUtils.convertQueryStringToJson((HttpServletRequest) webRequest.getNativeRequest()),
                parameter.getParameterType()
        );
    }
}
