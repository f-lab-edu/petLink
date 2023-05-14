package com.petlink.member.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateAccountForm {
	private final String username;
	private final String password;

}
