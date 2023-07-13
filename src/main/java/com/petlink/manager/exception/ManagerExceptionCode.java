package com.petlink.manager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ManagerExceptionCode {

    //존재하지 않는 펀딩 번호입니다.
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 매니저입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
