package com.petlink.funding.exception;

import org.springframework.http.HttpStatus;

import com.petlink.member.exception.MemberExceptionCode;

public class FundingException extends RuntimeException {
	private final MemberExceptionCode exceptionCode;

	public FundingException(MemberExceptionCode exceptionCode) {
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
