package com.petlink.common.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	MANAGER("ROLE_MANAGER"),
	MEMBER("ROLE_MEMBER");
	private final String role;
}
