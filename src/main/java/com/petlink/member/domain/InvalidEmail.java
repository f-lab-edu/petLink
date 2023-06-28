package com.petlink.member.domain;

import lombok.Getter;

@Getter
public enum InvalidEmail {
    PETLINK_COM("@petlink.com");

    private final String emailSuffix;

    InvalidEmail(String emailSuffix) {
        this.emailSuffix = emailSuffix;
    }
}
