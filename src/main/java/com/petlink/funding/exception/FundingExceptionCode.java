package com.petlink.funding.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundingExceptionCode {

	//존재하지 않는 펀딩 번호입니다.
	FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 펀딩 번호입니다."),
	FUNDING_ALREADY_CLOSED(HttpStatus.BAD_REQUEST, "펀딩이 종료되었습니다."),
	FUNDING_ALREADY_SUCCESS(HttpStatus.BAD_REQUEST, "펀딩이 성공되었습니다."),
	FUNDING_ALREADY_FAIL(HttpStatus.BAD_REQUEST, "펀딩이 실패되었습니다."),
	FUNDING_ALREADY_CANCEL(HttpStatus.BAD_REQUEST, "펀딩이 취소되었습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}