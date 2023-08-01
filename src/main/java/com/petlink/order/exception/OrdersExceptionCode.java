package com.petlink.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrdersExceptionCode {
    PAY_METHOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "결제수단을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
