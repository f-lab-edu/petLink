package com.petlink.common.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {
    private final TokenExceptionCode exceptionCode;

    public TokenException(TokenExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public HttpStatus getHttpStatus() {
        return exceptionCode.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
