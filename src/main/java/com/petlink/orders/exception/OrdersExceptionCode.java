package com.petlink.orders.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrdersExceptionCode {
    PAY_METHOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "결제수단을 찾을 수 없습니다."),
    REWARD_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "리워드가 부족합니다."),
    //현재 구매할 수 없습니다
    CANNOT_BUY_NOW(HttpStatus.BAD_REQUEST, "현재 구매할 수 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, "주문을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

