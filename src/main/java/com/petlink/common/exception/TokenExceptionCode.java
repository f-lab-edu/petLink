package com.petlink.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenExceptionCode {

    INVALID_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
    NULL_IS_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "NULL은 허용되지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
