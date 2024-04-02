package com.stupig.kms.user.vo;

import com.stupig.kms.common.vo.DatabaseOperateVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class UserMgmtDVO extends DatabaseOperateVO {
    private static final long serialVersionUID = 1431374788900426236L;

    /* 사용자일련번호 */
    private Long seq;
    /* ID */
    private String id;
    /* 비밀번호 */
    private String password;
    /* 비밀번호만료일시 */
    private LocalDateTime pwExpireDatetime;
    /* 비밀번호오류횟수 */
    private Integer pwWrongCount;
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
    /* 부서일련번호 */
    private Long groupSeq;
    /* 권한코드(00001) */
    private String auth;
    /* 승인자 */
    private Long approveUserSeq;
    /* 승인일시 */
    private LocalDateTime approveDatetime;
}
