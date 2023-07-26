package com.petlink.funding.item.exception;

import org.springframework.http.HttpStatus;

public class ItemException extends RuntimeException {
    private final ItemExceptionCode exceptionCode;

    public ItemException(ItemExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ItemException(ItemExceptionCode exceptionCode, String message) {
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
