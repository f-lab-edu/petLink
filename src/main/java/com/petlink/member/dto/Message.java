package com.petlink.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Message {
    DUPLICATED_NAME("이미 사용중인 이름입니다.", HttpStatus.CONFLICT),
    AVAILABLE_NAME("사용 가능한 이름입니다.", HttpStatus.OK),

    WITHDRAWAL_SUCCESS("회원 탈퇴가 완료되었습니다.", HttpStatus.OK);

    final String message;
    final HttpStatus httpStatus;
}
