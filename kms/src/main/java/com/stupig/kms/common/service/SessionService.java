package com.stupig.kms.common.service;

import com.stupig.kms.common.utils.SessionUtils;
import com.stupig.kms.common.vo.UserSessionVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SessionService {

    private final String USER_KEY = "USER";

    public void setUserSession(UserSessionVO userSession) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession httpSession = requestAttributes.getRequest().getSession();
        httpSession.setMaxInactiveInterval(10 * 60);   // Second
        requestAttributes.setAttribute(this.USER_KEY, userSession, RequestAttributes.SCOPE_SESSION);
    }

    public UserSessionVO getUserSession() {
        return (UserSessionVO) SessionUtils.getSessionInfo(this.USER_KEY);
    }

    public void removeUserSession() {
        SessionUtils.removeSessionInfo(this.USER_KEY);
    }
}
