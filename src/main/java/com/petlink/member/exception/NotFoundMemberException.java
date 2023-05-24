package com.petlink.member.exception;

public class NotFoundMemberException extends RuntimeException {
	public NotFoundMemberException(String message) {
		super(message);
	}

	public NotFoundMemberException() {
		super("회원을 찾을 수 없습니다..");
	}
}
