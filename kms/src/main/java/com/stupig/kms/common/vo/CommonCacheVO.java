package com.stupig.kms.common.vo;

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
public class CommonCacheVO implements Serializable {
    private static final long serialVersionUID = -4352952120355267804L;

    /* Session ID */
    private String sessionId;
    /* Expired Time */
    private LocalDateTime expiredTime;

    public CommonCacheVO(String sessionId, LocalDateTime expiredTime) {
        this.sessionId = sessionId;
        this.expiredTime = expiredTime;
    }
}
