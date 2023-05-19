package com.petlink.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	private String address;
	private String detailAddress;
	private String zipCode;

	public Address(String address, String detailAddress, String zipCode) {
		this.address = address;
		this.detailAddress = detailAddress;
		this.zipCode = zipCode;
	}
}
