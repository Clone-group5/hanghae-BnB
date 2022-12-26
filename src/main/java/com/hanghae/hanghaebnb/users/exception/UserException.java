package com.hanghae.hanghaebnb.users.exception;

import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.NOT_FOUND_MATCH_USER_INFO;
}
