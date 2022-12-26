package com.hanghae.hanghaebnb.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //book
    NOT_FOUND_ROOM_EXCEPTION(404, "존재하지 않는 숙소입니다."),
    INVALID_EMAIL(400, "이메일 형식이 맞지 않습니다."),
    DUPLICATE_EMAIL(400, "중복된 이메일입니다."),
    INVALID_PASSWORD(400, "비밀번호 형식이 맞지 않습니다."),
    AUTHORIZATION_FAIL(401, "권한이 없습니다.");

    private final int StatusCode;
    private final String msg;

}
