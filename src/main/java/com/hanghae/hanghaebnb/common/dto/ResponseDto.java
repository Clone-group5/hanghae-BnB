package com.hanghae.hanghaebnb.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private int StatusCode;
    private String msg;
    private T data;
}
