package com.petlink.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

	//존재하지 않는 펀딩 아이디입니다.
	FUNDING_NOT_FOUND(404, "존재하지 않는 펀딩 아이디입니다.");

	private final int status;
	private final String message;
}
