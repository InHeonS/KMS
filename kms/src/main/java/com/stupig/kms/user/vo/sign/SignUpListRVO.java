package com.stupig.kms.user.vo.sign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignUpListRVO implements Serializable {
    private static final long serialVersionUID = -7478313351390004632L;

    /* 사용자일련번호 */
    @JsonProperty("seq")
    private String seq;
    /* ID */
    @JsonProperty("id")
    private String id;
    /* 이름 */
    @JsonProperty("name")
    private String name;
    /* 회사명 */
    @JsonProperty("comp_name")
    private String compName;
    /* 사번 */
    @JsonProperty("comp_id")
    private String compId;
    /* 전화번호 */
    @JsonProperty("phone")
    private String phone;
    /* 이메일 */
    @JsonProperty("email")
    private String email;
}
