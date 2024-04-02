package com.stupig.kms.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stupig.kms.common.constants.ResponseCode;
import com.stupig.kms.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO<T> implements Serializable {
    private static final long serialVersionUID = -5645565245831680392L;

    /* 응답코드 */
    @JsonProperty("res_code")
    private String resCode;
    /* 응답메세지 */
    @JsonProperty("res_msg")
    private String resMsg;

    @JsonProperty("data")
    private T data;

    public ResponseVO() {
        this.setSuccess();
    }

    public ResponseVO(String code) {
        this.setResponse(code);
    }

    public void setSuccess() {
        this.setResponse(ResponseCode.SUCCESS);
    }

    public void setException(String exceptionMessage) {
        this.setResponse(ResponseCode.EXCEPTION);
        this.resMsg += exceptionMessage;
    }

    public void setResponse(String code) {
        this.resCode = code;

        switch (StringUtils.nvl(code)) {
            case ResponseCode.SUCCESS:
                this.resMsg = "정상처리";
                break;
            case ResponseCode.INVALID_PARAMETER:
                this.resMsg = "요청 파라미터 오류";
                break;
            case ResponseCode.MULTIPLE_SIGN_IN:
                this.resMsg = "중복 로그인";
                break;
            case ResponseCode.NON_EXIST_ID:
                this.resMsg = "존재하지 않는 아이디";
                break;
            case ResponseCode.WRONG_PASSWORD:
                this.resMsg = "비밀번호 오류";
                break;
            case ResponseCode.PASSWORD_EXPIRED:
                this.resMsg = "비밀번호 기간 만료";
                break;
            case ResponseCode.PASSWORD_INPUT_COUNT_OVER:
                this.resMsg = "비밀번호 횟수 초과로 관리 문의";
                break;
            case ResponseCode.DUPLICATE_ID:
                this.resMsg = "중복아이디";
                break;
            case ResponseCode.APPROVED_USER:
                this.resMsg = "기승인 사용자";
                break;
            case ResponseCode.LOGIN_TIMEOUT:
                this.resMsg = "로그인 시간 만료";
                break;
            case ResponseCode.SESSION_ERROR:
                this.resMsg = "세션 오류";
                break;
            case ResponseCode.EXCEPTION:
                this.resMsg = "EXCEPTION - ";
                break;
            case ResponseCode.ETC_ERROR:
                this.resMsg = "기타 오류";
                break;
        }
    }
}
