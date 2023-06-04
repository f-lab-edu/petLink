package com.petlink.common.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
	private final CommonExceptionCode exceptionCode;

	public CommonException(CommonExceptionCode exceptionCode) {
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
