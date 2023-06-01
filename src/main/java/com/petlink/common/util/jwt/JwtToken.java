package com.petlink.common.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtToken {
	JWT_TOKEN("jwt-token");

	private final String tokenName;
}
