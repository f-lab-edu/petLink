package com.petlink.member.exception;

public class NotMatchedPasswordException extends RuntimeException {
	public NotMatchedPasswordException(String message) {
		super(message);
	}

	public NotMatchedPasswordException() {
		super("계정 정보가 일치하지 않습니다.");
	}
}
