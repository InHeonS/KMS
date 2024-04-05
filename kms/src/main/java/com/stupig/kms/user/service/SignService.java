package com.stupig.kms.user.service;

import com.stupig.kms.common.cache.UserCacheResource;
import com.stupig.kms.common.constants.ResponseCode;
import com.stupig.kms.common.service.SessionService;
import com.stupig.kms.common.utils.ThreadUtils;
import com.stupig.kms.common.vo.ResponseListVO;
import com.stupig.kms.common.vo.ResponseVO;
import com.stupig.kms.common.vo.UserSessionVO;
import com.stupig.kms.user.mapper.SignMapper;
import com.stupig.kms.user.vo.UserMgmtPVO;
import com.stupig.kms.user.vo.UserMgmtRVO;
import com.stupig.kms.user.vo.sign.SignPVO;
import com.stupig.kms.user.vo.sign.SignUpListPVO;
import com.stupig.kms.user.vo.sign.SignUpListRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class SignService {

    private final SessionService sessionService;
    private final UserCacheResource userCacheResource;
    private final SignMapper signMapper;

    @Autowired
    public SignService(SessionService sessionService, UserCacheResource userCacheResource, SignMapper signMapper) {
        this.sessionService = sessionService;
        this.userCacheResource = userCacheResource;
        this.signMapper = signMapper;
    }

    private final int passwordMaxCount = 5;

    /**
     * [Service]
     * 로그인 요청
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseVO<Void> signIn(SignPVO pvo, ResponseVO<Void> response) {
        final String methodName = this.getClass().getSimpleName() + "." + ThreadUtils.getMethodName();

        // 1. Sign in 검증
        UserMgmtRVO userMgmtRVO = signMapper.selectUserInfoById(pvo.getId());
        if (userMgmtRVO == null) {
            response.setResponse(ResponseCode.NON_EXIST_ID);
            return response;
        } else if (userMgmtRVO.getPwWrongCount() != null && userMgmtRVO.getPwWrongCount() >= this.passwordMaxCount) {
            response.setResponse(ResponseCode.PASSWORD_INPUT_COUNT_OVER);
            return response;
        } else if (!pvo.getPassword().equals(userMgmtRVO.getPassword())) {
            Integer wrongCount = Optional.ofNullable(userMgmtRVO.getPwWrongCount()).orElse(0) + 1;

            response.setResponse(
                    this.updatePasswordCount(
                            userMgmtRVO.getSeq(),
                            wrongCount,
                            methodName
                    ) ? ResponseCode.WRONG_PASSWORD : ResponseCode.ETC_ERROR
            );
            response.setResMsg(response.getResMsg() + "(" + wrongCount + "/" + this.passwordMaxCount + ")");

            return response;
        }

        // 2. 비밀번호 오류 횟수 초기화
        if (!this.updatePasswordCount(userMgmtRVO.getSeq(), 0, methodName)) {
            System.out.println("userMgmtRVO : " + userMgmtRVO );
        	response.setResponse(ResponseCode.ETC_ERROR);
            return response;
        }

        // 3. 비밀번호 만료 확인
        if (userMgmtRVO.getPwExpireDatetime() != null
                && !LocalDateTime.now().isBefore(userMgmtRVO.getPwExpireDatetime())
        ) {
            response.setResponse(ResponseCode.PASSWORD_EXPIRED);
            return response;
        }

        // 4. Session - 고객 정보 추가
        sessionService.setUserSession(new UserSessionVO(userMgmtRVO));

        // 5. Cache 등록
        userCacheResource.putUserCache(userMgmtRVO.getSeq());

        response.setSuccess();
        return response;
    }

    /**
     * [Service]
     * 로그아웃 요청
     *
     * @return
     */
    public ResponseVO<Void> signOut(ResponseVO<Void> response) {
        UserSessionVO userSession = sessionService.getUserSession();

        if (userSession != null && userSession.getSeq() != null) {
            // 1. Cache 제거
            userCacheResource.removeUserCache(sessionService.getUserSession().getSeq());
        }

        // 2. Session 제거
        sessionService.removeUserSession();

        response.setSuccess();
        return response;
    }

    /**
     * [Service]
     * 회원가입 요청
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseVO<Void> signUp(SignPVO pvo, ResponseVO<Void> response) {
        final String methodName = this.getClass().getSimpleName() + "." + ThreadUtils.getMethodName();

        // 1. id 중복 확인
        if (signMapper.selectUserInfoById(pvo.getId()) != null) {
            response.setResponse(ResponseCode.DUPLICATE_ID);
            return response;
        }

        // 2. 사용자 정보 등록
        UserMgmtPVO userMgmtPVO = new UserMgmtPVO();
        userMgmtPVO.setId(pvo.getId());
        userMgmtPVO.setPassword(pvo.getPassword());
        userMgmtPVO.setName(pvo.getName());
        userMgmtPVO.setComp_name(pvo.getComp_name());
        userMgmtPVO.setComp_id(pvo.getComp_id());
        userMgmtPVO.setPhone(pvo.getPhone());
        userMgmtPVO.setEmail(pvo.getEmail());
        userMgmtPVO.setOperator(methodName);

        response.setResponse(
                signMapper.insertUser(userMgmtPVO) ? ResponseCode.SUCCESS : ResponseCode.ETC_ERROR
        );

        return response;
    }

    /**
     * [Service]
     * 회원가입 아이디 중복 확인
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseVO<Void> idCheck(SignPVO pvo, ResponseVO<Void> response) {
        // 1. id 존재 확인
        response.setResponse(
                signMapper.selectUserInfoById(pvo.getId()) == null
                        ? ResponseCode.SUCCESS : ResponseCode.DUPLICATE_ID
        );

        return response;
    }

    /**
     * [Service]
     * 회원가입 요청 승인
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseVO<Void> signConfirm(SignPVO pvo, ResponseVO<Void> response) {
        final String methodName = this.getClass().getSimpleName() + "." + ThreadUtils.getMethodName();
        UserSessionVO userSession = sessionService.getUserSession();

        // 1. 처리 대상 검증
        UserMgmtRVO userMgmtRVO = signMapper.selectUserInfo(pvo.getSeq());
        if (userMgmtRVO == null) {
            response.setResponse(ResponseCode.NON_EXIST_ID);
            return response;
        } else if (userMgmtRVO.getApproveUserSeq() != null) {
            response.setResponse(ResponseCode.APPROVED_USER);
            return response;
        }

        // 2. 승인 처리
        UserMgmtPVO userMgmtPVO = new UserMgmtPVO();
        userMgmtPVO.setSeq(pvo.getSeq());
        userMgmtPVO.setApproveUserSeq(userSession.getSeq());
        userMgmtPVO.setApproveDatetime(LocalDateTime.now());
        userMgmtPVO.setGroupSeq(pvo.getGroupSeq());
        userMgmtPVO.setAuth(pvo.getAuth());
        userMgmtPVO.setLastOperator(methodName);

        try {
            response.setResponse(
                    signMapper.signConfirm(userMgmtPVO) ? ResponseCode.SUCCESS : ResponseCode.ETC_ERROR
            );
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            response.setException(e.getMessage());
        }

        return response;
    }

    /**
     * [Service]
     * 회원가입 요청 리스트
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseListVO<SignUpListRVO> signUpList(SignUpListPVO pvo, ResponseListVO<SignUpListRVO> response) {
        // 1. Page 처리
        if (pvo.getPage() == null) {
            pvo.setPage(1);
        }
        if (pvo.getRowCount() == null) {
            pvo.setRowCount(20);
        }

        // 2. Set Response
        response.setData(signMapper.selectSignUpList(pvo));
        response.setTotalCount(signMapper.selectSignUpListCount(pvo));
        response.setPage(pvo.getPage());
        response.setRowCount(pvo.getRowCount());
        response.setSuccess();

        return response;
    }

    /**
     * [Method]
     * 비밀번호 횟수 변경
     *
     * @param userSeq
     * @param pwWrongCount
     * @param operator
     * @return
     */
    private boolean updatePasswordCount(Long seq, int pwWrongCount, String operator) {
        UserMgmtPVO userMgmtPVO = new UserMgmtPVO();
        userMgmtPVO.setSeq(seq);
        userMgmtPVO.setPwWrongCount(pwWrongCount);
        userMgmtPVO.setLastOperator(operator);

        return signMapper.updatePasswordCount(userMgmtPVO);
    }


}
