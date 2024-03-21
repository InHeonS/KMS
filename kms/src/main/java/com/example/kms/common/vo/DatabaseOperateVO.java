package com.example.kms.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DatabaseOperateVO implements Serializable {
    private static final long serialVersionUID = -2796643291340256995L;

    /* 최초조작자 */
    private String firstOperator;
    /* 최초조작상세일시 */
    private LocalDateTime firstOperateDetailDate;
    /* 최종조작자 */
    private String lastOperator;
    /* 최종조작상세일시 */
    private LocalDateTime lastOperateDetailDate;

    public void setOperator(String operator) {
        this.firstOperator = operator;
        this.lastOperator = operator;
    }

}
