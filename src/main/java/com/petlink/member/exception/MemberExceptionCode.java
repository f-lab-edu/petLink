package com.petlink.member.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberExceptionCode {

	ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 등록된 회원입니다."),
	NOT_FOUND_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	NOT_MATCHED_INFOMATION(HttpStatus.UNAUTHORIZED, "정보가 일치하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
