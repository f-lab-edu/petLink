package com.petlink.member.domain;

import lombok.Getter;

public enum MemberStatus {

    ACTIVE("활성 회원"),
    INACTIVE("비활성 회원");

    @Getter
    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }
}
