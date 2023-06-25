package com.petlink.funding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundingState {
    SCHEDULED("예정"), PROGRESS("진행중"), END("종료"), CANCEL("취소");
    private final String state;
}
