package com.example.kms.user.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kms.common.constant.ResponseCode;
import com.example.kms.common.utils.StringUtils;
import com.example.kms.common.vo.ResponseVO;
import com.example.kms.user.service.SignService;
import com.example.kms.user.vo.sign.SignPVO;

@RestController
@RequestMapping("/api/user")
public class SignController {

    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    /**
     * U0001
     * POST /api/user/signIn
     *
     * @param pvo
     * @return
     */
    @PostMapping("/signIn")
    public ResponseVO<Void> signIn(@RequestBody SignPVO pvo) {
        ResponseVO<Void> response = new ResponseVO<>();

        if (StringUtils.isAnyNullOrBlank(pvo.getId(), pvo.getPassword())) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }

        response = signService.signIn(pvo, response);

        return response;
    }
    
    /**
     * U0002
     * POST /api/user/signOut
     *
     * @return
     */
    @PostMapping("/signOut")
    public ResponseVO<Void> signOut() {
        return signService.signOut();
    }

    /**
     * U0003
     * POST /api/user/signUp
     *
     * @param pvo
     * @return
     */
    @PostMapping("/signUp")
    public ResponseVO<Void> signUp(@RequestBody SignPVO pvo) {
        ResponseVO<Void> response = new ResponseVO<>();

        if (StringUtils.isAnyNullOrBlank(
                pvo.getId(),
                pvo.getPassword(),
                pvo.getName()
        )) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }

        response = signService.signUp(pvo, response);

        return response;
    }
    
    /**
     * U0004
     * POST /api/user/idCheck
     *
     * @param pvo
     * @return
     */
    @PostMapping("/idCheck")
    public ResponseVO<Void> idCheck(@RequestBody SignPVO pvo) {
        ResponseVO<Void> response = new ResponseVO<>();

        if (StringUtils.isNullOrBlank(pvo.getId())) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }

        response = signService.idCheck(pvo, response);

        return response;
    }
    
    /**
     * U0005
     * POST /api/user/signOut
     *
     * @return
     * @throws Exception 
     */
    @PostMapping("/signUpList")
    public ResponseVO<Void> signUpList(@RequestBody String data) throws Exception {
        ResponseVO<Void> response = new ResponseVO<>();

        if (data == null || StringUtils.isAnyNullOrBlank(data)) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }
        
        response = signService.signUpList(data, response);

        return response;
    }
    
    
    /**
     * U0006
     * POST /api/user/signConfirm
     *
     * @param pvo
     * @return
     */
    @PostMapping("/signConfirm")
    public ResponseVO<Void> signConfirm(@RequestBody SignPVO pvo) {
        ResponseVO<Void> response = new ResponseVO<>();

        if (pvo.getIdx() == null || StringUtils.isNullOrBlank(pvo.getAuth())) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }
        
        response = signService.signConfirm(pvo, response);

        return response;
    }


}
