package com.petlink.member.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {
	private final MemberExceptionCode exceptionCode;

	public MemberException(MemberExceptionCode exceptionCode) {
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
