package com.example.kms.user.vo.sign;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpList {
    /* 사용자일련번호 */
    private Long idx;
    /* 유저이름 */
    private String id;
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
}
