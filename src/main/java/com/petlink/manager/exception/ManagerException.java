package com.petlink.manager.exception;

import org.springframework.http.HttpStatus;

public class ManagerException extends RuntimeException {
    private final ManagerExceptionCode exceptionCode;

    public ManagerException(ManagerExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ManagerException(ManagerExceptionCode exceptionCode, String message) {
        super(exceptionCode.getMessage() + message);
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
