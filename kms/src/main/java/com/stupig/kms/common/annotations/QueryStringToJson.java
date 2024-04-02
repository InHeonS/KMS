package com.stupig.kms.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})	// 해당 Annotation이 생성될 위치 지정
@Retention(RetentionPolicy.RUNTIME)	// Annotation 유지 정책을 설정
public @interface QueryStringToJson {
}
