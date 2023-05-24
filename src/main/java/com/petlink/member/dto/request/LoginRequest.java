package com.petlink.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

	@NotBlank(message = "이메일을 입력해주세요.")
	String email;
	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password;
}
