package com.petlink.order.funding.exception;

import org.springframework.http.HttpStatus;

public class FundingException extends RuntimeException {
	private final FundingExceptionCode exceptionCode;

	public FundingException(FundingExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public FundingException(FundingExceptionCode exceptionCode, String message) {
		super(exceptionCode.getMessage() + message);
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
