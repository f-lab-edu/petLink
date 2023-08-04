package com.petlink.funding.item.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ItemExceptionCode {

    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리워드가 존재하지 않습니다"),
    ITEM_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "해당 리워드의 재고가 부족합니다"),
    ITEM_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "해당 리워드는 현재 판매가 불가능합니다"),
    ITEM_MAX_BUY_COUNT_EXCEEDED(HttpStatus.BAD_REQUEST, "해당 리워드의 최대 구매 수량을 초과하였습니다"),
    ITEM_NOT_BELONG_TO_PROJECT(HttpStatus.BAD_REQUEST, "해당 리워드는 현재 프로젝트의 소유가 아닙니다"),
    ITEM_ALREADY_SOLD_OUT(HttpStatus.BAD_REQUEST, "해당 리워드는 이미 모두 판매되었습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
