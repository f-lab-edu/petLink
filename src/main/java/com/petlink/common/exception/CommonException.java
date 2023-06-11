package com.petlink.common.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CommonException(ExceptionCode exceptionCode) {
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
