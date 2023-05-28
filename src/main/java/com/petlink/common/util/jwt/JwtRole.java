package com.petlink.common.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtRole {
	MANAGER("매니저"),
	MEMBER("회원");

	private final String role;
}
