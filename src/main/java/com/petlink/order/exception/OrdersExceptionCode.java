package com.petlink.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrdersExceptionCode {

    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 등록된 회원입니다."),
    NOT_FOUND_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    NOT_MATCHED_INFOMATION(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다."),
    ALREADY_USED_NAME(HttpStatus.CONFLICT, "이미 사용중인 이름입니다."),
    NOT_AVAILABLE_EMAIL(HttpStatus.CONFLICT, "사용할 수 없는 이메일입니다."),
    ALREADY_WITHDRAWAL_MEMBER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
