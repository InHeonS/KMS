package com.stupig.kms.user.vo.sign;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class testRVO implements Serializable{
	private static final long serialVersionUID = 1203930562847589186L;
	
	/* 사용자일련번호 */
    @JsonProperty("seq")
    private String seq;
    /* ID */
    @JsonProperty("id")
    private String id;
    /* 이름 */
    @JsonProperty("name")
    private String name;
    /* 전화번호 */
    @JsonProperty("phone")
    private String phone;
    /* 이메일 */
    @JsonProperty("email")
    private String email;
}
