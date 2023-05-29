package com.petlink.common.exception;

public class GlobalException extends RuntimeException {

	private final ExceptionCode exceptionCode;

	public GlobalException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public int getStatus() {
		return exceptionCode.getStatus();
	}

	@Override
	public String getMessage() {
		return exceptionCode.getMessage();
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}
}
