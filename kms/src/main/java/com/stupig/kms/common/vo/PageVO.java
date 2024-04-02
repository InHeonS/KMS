package com.stupig.kms.common.vo;

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
public class PageVO implements Serializable {
    private static final long serialVersionUID = 93347901915912813L;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("row_count")
    private Integer rowCount;
}
