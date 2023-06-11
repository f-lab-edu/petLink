package com.petlink.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum Message {
    DUPLICATED_NAME("이미 사용중인 이름입니다."),
    AVAILABLE_NAME("사용 가능한 이름입니다.");

    final String message;
}
