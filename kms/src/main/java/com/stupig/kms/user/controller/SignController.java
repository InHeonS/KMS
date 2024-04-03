package com.stupig.kms.user.controller;

import com.stupig.kms.common.annotations.QueryStringToJson;
import com.stupig.kms.common.constants.ResponseCode;
import com.stupig.kms.common.utils.StringUtils;
import com.stupig.kms.common.vo.ResponseListVO;
import com.stupig.kms.common.vo.ResponseVO;
import com.stupig.kms.user.service.SignService;
import com.stupig.kms.user.vo.sign.SignPVO;
import com.stupig.kms.user.vo.sign.SignUpListPVO;
import com.stupig.kms.user.vo.sign.SignUpListRVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("실행 위치 확인용 controller");
        if (StringUtils.isAnyNullOrBlank(pvo.getId(), pvo.getPassword())) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }

        response = signService.signIn(pvo, response);

        return response;
    }

    /**
     * U0002
     * GET /api/user/signOut
     *
     * @return
     */
    @GetMapping("/signOut")
    public ResponseVO<Void> signOut() {
        ResponseVO<Void> response = new ResponseVO<>();

        response = signService.signOut(response);

        return response;
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
     * POST /api/user/signUpList
     *
     * @param pvo
     * @return
     */
    @PostMapping("/signUpList")
    public ResponseListVO<SignUpListRVO> signUpList(@QueryStringToJson SignUpListPVO pvo) {
        ResponseListVO<SignUpListRVO> response = new ResponseListVO<>();

        response = signService.signUpList(pvo, response);

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

        if (pvo.getSeq() == null || StringUtils.isNullOrBlank(pvo.getAuth())) {
            response.setResponse(ResponseCode.INVALID_PARAMETER);
            return response;
        }

        response = signService.signConfirm(pvo, response);

        return response;
    }
    
    /**
     * U0000
     * POST /api/user/signUpList
     *
     * @param pvo
     * @return
     */
    @PostMapping("/test")
    public ResponseListVO<SignUpListRVO> test(@QueryStringToJson SignUpListPVO pvo) {
        ResponseListVO<SignUpListRVO> response = new ResponseListVO<>();
        
        response = signService.test(pvo, response);

        return response;
    }
}
