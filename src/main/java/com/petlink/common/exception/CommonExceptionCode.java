package com.petlink.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonExceptionCode {

	//존재하지 않는 펀딩 아이디입니다.
	INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "날짜 형식이 올바르지 않습니다."),
	NULL_IS_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "NULL은 허용되지 않습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
