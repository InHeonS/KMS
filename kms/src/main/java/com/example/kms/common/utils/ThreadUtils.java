package com.example.kms.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtils {

    public static String getMethodName() {
        String methodName;

        try {
            methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        } catch (Throwable t) {
            log.error(t.getMessage());
            methodName = null;
        }

        return StringUtils.nvl(methodName);
    }
}
