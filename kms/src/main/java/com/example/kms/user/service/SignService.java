package com.example.kms.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.kms.common.constant.ResponseCode;
import com.example.kms.common.utils.ThreadUtils;
import com.example.kms.common.vo.ResponseVO;
import com.example.kms.user.mapper.SignMapper;
import com.example.kms.user.vo.UserMgmtPVO;
import com.example.kms.user.vo.UserMgmtRVO;
import com.example.kms.user.vo.sign.SignPVO;
import com.example.kms.user.vo.sign.SignUpList;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignService {

    private final SignMapper signMapper;

    @Autowired
    public SignService(SignMapper signMapper) {
        this.signMapper = signMapper;
    }

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
        } else if (userMgmtRVO.getPwWrongCount() != null && userMgmtRVO.getPwWrongCount() >= 5) {
            response.setResponse(ResponseCode.PASSWORD_INPUT_COUNT_OVER);
            return response;
        } else if (!pvo.getPassword().equals(userMgmtRVO.getPassword())) {
            response.setResponse(
                    this.updatePasswordCount(
                            userMgmtRVO.getIdx(),
                            Optional.ofNullable(userMgmtRVO.getPwWrongCount()).orElse(0) + 1,
                            methodName
                    ) ? ResponseCode.PASSWORD_ERROR : ResponseCode.ETC_ERROR
            );

            return response;
        }

        // 2. 비밀번호 오류 횟수 초기화
        if (!this.updatePasswordCount(userMgmtRVO.getIdx(), 0, methodName)) {
            response.setResponse(ResponseCode.ETC_ERROR);
            return response;
        }

        // 3. 비밀번호 만료 확인
        if (userMgmtRVO.getPwExpireDatetime() != null
                && !LocalDateTime.now().isBefore(userMgmtRVO.getPwExpireDatetime())	//DB에 pw_expired_datetime을 설정하여 자동으로 90일 뒤 날짜로 나오게 세팅 (CURRENT_DATE() + INTERVAL 90 DAY)
        ) {
            response.setResponse(ResponseCode.PASSWORD_EXPIRED);
            return response;
        }

        // FIXME : Session 등록 처리 / Token - JWT -> Spring Security : 2일째 삽질 중 준나 어려워

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
     * 회원가입 요청 승인
     *
     * @param pvo
     * @param response
     * @return
     */
    public ResponseVO<Void> signConfirm(SignPVO pvo, ResponseVO<Void> response) {
        final String methodName = this.getClass().getSimpleName() + "." + ThreadUtils.getMethodName();

        // 1. 처리 대상 검증
        UserMgmtRVO userMgmtRVO = signMapper.selectUserInfo(pvo.getIdx());
        if (userMgmtRVO == null) {
            response.setResponse(ResponseCode.NON_EXIST_USER);
            return response;
        } else if (userMgmtRVO.getApproveUserSeq() != null) {
            response.setResponse(ResponseCode.APPROVED_USER);
            return response;
        }

        // 2. 승인 처리
        UserMgmtPVO userMgmtPVO = new UserMgmtPVO();
        userMgmtPVO.setIdx(pvo.getIdx());
        userMgmtPVO.setApproveUserSeq(1L);  // FIXME : Session 추출
        userMgmtPVO.setApproveDatetime(LocalDateTime.now());
        userMgmtPVO.setGroupSeq(pvo.getGroupSeq());
        userMgmtPVO.setAuth(pvo.getAuth());
        userMgmtPVO.setLastOperator(methodName);

        try {
            response.setResponse(
                    signMapper.signConfirm(userMgmtPVO) ? ResponseCode.SUCCESS : ResponseCode.ETC_ERROR
            );
        } catch (DataIntegrityViolationException dive) {
            log.error(dive.getMessage(), dive);
            response.setException(dive.getMessage());
        }

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
     * 로그아웃 요청
     *
     * @return
     */
    public ResponseVO<Void> signOut() {
        ResponseVO<Void> response = new ResponseVO<>();

        // FIXME : Session 파기 처리

        response.setSuccess();
        return response;
    }

    /**
     * [Service]
     * 회원가입 요청 리스트
     *
     * @param page, row_count
     * @param response
     * @return
     */
    
	public ResponseVO<Void> signUpList(String data, ResponseVO<Void> response) throws Exception{
       final String methodName = this.getClass().getSimpleName() + "." + ThreadUtils.getMethodName();
       
       int page = 0;
       int row_count = 0;
       
       JSONParser parser = new JSONParser();
       JSONObject jsonObject = (JSONObject) parser.parse(data);
       
       page = Integer.parseInt(jsonObject.get("page").toString());
       row_count = Integer.parseInt(jsonObject.get("row_count").toString());
       if(row_count == 0) {
    	   row_count = 20;
       }
       
       List<SignUpList> userList = signMapper.selectUserList(page * row_count - 1,row_count);
       
       ObjectMapper objectMapper = new ObjectMapper();

       // JSON 배열을 담을 리스트 생성
       ArrayList jsonList = new ArrayList<>();

       // 리스트를 순회하며 각 요소를 JSON 객체로 변환하여 JSON 배열에 추가
       for (SignUpList signUpList : userList) {
           // JSON 객체로 변환
           String jsonGroup = objectMapper.writeValueAsString(signUpList);
           // JSON 배열에 추가
           jsonList.add(jsonGroup);
       }
       
//       System.out.println(jsonList);
//       System.out.println(jsonList.size());
       
       UserMgmtRVO userMgmtRVO = new UserMgmtRVO();
       
       System.out.println(userMgmtRVO);
       response.setResponse(ResponseCode.SUCCESS);
       
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
    private boolean updatePasswordCount(Long userSeq, int pwWrongCount, String operator) {
        UserMgmtPVO userMgmtPVO = new UserMgmtPVO();
        userMgmtPVO.setIdx(userSeq);
        userMgmtPVO.setPwWrongCount(pwWrongCount);
        userMgmtPVO.setLastOperator(operator);

        return signMapper.updatePasswordCount(userMgmtPVO);
    }
}
