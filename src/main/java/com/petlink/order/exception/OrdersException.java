package com.petlink.order.exception;

import org.springframework.http.HttpStatus;

public class OrdersException extends RuntimeException {
    private final OrdersExceptionCode exceptionCode;

    public OrdersException(OrdersExceptionCode exceptionCode) {
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
