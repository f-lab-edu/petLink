package com.petlink.common.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	MANAGER("ROLE_MANAGER"),
	MEMBER("ROLE_MEMBER");

	private final String role;
}
