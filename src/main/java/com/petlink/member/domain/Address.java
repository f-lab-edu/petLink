package com.petlink.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	@Column(name = "member_address")
	private String addressInfo;
	private String detailAddress;
	private String zipCode;

	public Address(String zipCode, String addressInfo, String detailAddress) {
		this.zipCode = zipCode;
		this.addressInfo = addressInfo;
		this.detailAddress = detailAddress;
	}
}
