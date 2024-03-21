package com.example.kms.user.vo.sign;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignPVO implements Serializable {
    private static final long serialVersionUID = -7595135804058685912L;

    /* 사용자일련번호 */
    private Long idx;
    /* 유저이름 */
    private String id;
    /* 비밀번호 */
    private String password;
    /* 이름 */
    private String name;
    /* 회사명 */
    private String comp_name;
    /* 사번 */
    private String comp_id;
    /* 전화번호 */
    private String phone;
    /* 이메일 */
    private String email;
    /* 부서 */
    private Long groupSeq;
    /* 권한코드 */
    private String auth;

}
