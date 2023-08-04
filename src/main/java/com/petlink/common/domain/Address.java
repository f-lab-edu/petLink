package com.petlink.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Column(name = "address")
    private String addressInfo;
    @Column(name = "detail_address")
    private String detailAddress;
    @Column(name = "zip_code")
    private String zipCode;

    // todo 정적팩토리 패턴으로 변경 필요
    public Address(String zipCode, String addressInfo, String detailAddress) {
        this.zipCode = zipCode;
        this.addressInfo = addressInfo;
        this.detailAddress = detailAddress;
    }

    public static Address of(String zipCode, String addressInfo, String detailAddress) {
        return new Address(zipCode, addressInfo, detailAddress);
    }
}
