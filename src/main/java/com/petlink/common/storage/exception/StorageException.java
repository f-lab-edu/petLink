package com.petlink.common.storage.exception;

import org.springframework.http.HttpStatus;

public class StorageException extends RuntimeException {
    private final StorageExceptionCode exceptionCode;

    public StorageException(StorageExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public StorageException(StorageExceptionCode exceptionCode, String message) {
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
