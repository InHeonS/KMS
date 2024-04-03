package com.stupig.kms.common.interceptor;

import com.stupig.kms.common.cache.UserCacheResource;
import com.stupig.kms.common.constants.ResponseCode;
import com.stupig.kms.common.service.SessionService;
import com.stupig.kms.common.utils.ResponseUtils;
import com.stupig.kms.common.utils.SessionUtils;
import com.stupig.kms.common.vo.CommonCacheVO;
import com.stupig.kms.common.vo.ResponseVO;
import com.stupig.kms.common.vo.UserSessionVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Slf4j
public class UserCacheInterceptor implements HandlerInterceptor {

    private final UserCacheResource userCacheResource;
    private final SessionService sessionService;

    public UserCacheInterceptor(UserCacheResource userCacheResource, SessionService sessionService) {
        this.userCacheResource = userCacheResource;
        this.sessionService = sessionService;
        System.out.println("실행 위치 확인용 Interceptor");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {	// preHandle : controller 호출 전 실행 , postHandle : controller 호출 후 실행
        // 1. Session 검증
        UserSessionVO userSession = sessionService.getUserSession();
        if (userSession == null || userSession.getSeq() == null) {
            ResponseUtils.responseError(response,  new ResponseVO<Void>(ResponseCode.SESSION_ERROR));
            return false;
        }

        // 2. Cache 검증
        CommonCacheVO userCache = userCacheResource.getUserCache(userSession.getSeq());
        if (userCache == null) {
            /* 비정상 접근 */
            // 1. Session 저장 정보 파기
            sessionService.removeUserSession();

            ResponseUtils.responseError(response,  new ResponseVO<Void>(ResponseCode.ETC_ERROR));
            return false;
        } else if (!SessionUtils.getSessionId().equals(userCache.getSessionId())) {
            /* 중복 로그인 */
            ResponseUtils.responseError(response,  new ResponseVO<Void>(ResponseCode.MULTIPLE_SIGN_IN));
            return false;
        } else if (userCache.getExpiredTime() == null || userCache.getExpiredTime().isBefore(LocalDateTime.now())) {
            /* 로그인 만료 */
            // 1. Session 저장 정보 파기
            sessionService.removeUserSession();

            // 2. Cache 정보 파기
            userCacheResource.removeUserCache(userSession.getSeq());

            ResponseUtils.responseError(response,  new ResponseVO<Void>(ResponseCode.LOGIN_TIMEOUT));
            return false;
        }

        /* 정상 접근 */
        // 1. Cache 만료 시간 수정
        userCacheResource.putUserCache(userSession.getSeq());

        return true;
    }
}
