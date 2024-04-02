package com.stupig.kms.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseListVO<T> extends ResponseVO<List<T>> {
    private static final long serialVersionUID = 2645788496637410150L;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("row_count")
    private Integer rowCount;

    public ResponseListVO() {
        super();
    }

    public ResponseListVO(String code) {
        super(code);
    }
}
