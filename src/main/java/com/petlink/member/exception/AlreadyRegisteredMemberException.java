package com.petlink.member.exception;

public class AlreadyRegisteredMemberException extends RuntimeException {
	public AlreadyRegisteredMemberException(String message) {
		super(message);
	}

	public AlreadyRegisteredMemberException() {
		super("이미 등록된 회원입니다.");
	}
}
