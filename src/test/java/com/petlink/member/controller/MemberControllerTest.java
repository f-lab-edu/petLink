package com.petlink.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController 테스트")
class MemberControllerTest {

	@InjectMocks
	private MemberController memberController;

	@Mock
	private MemberService memberService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입을 진행할 수 있다.")
	void signUpTest() {
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.name("TestName")
			.email("test@example.com")
			.password("password")
			.tel("1234567890")
			.zipCode("12345")
			.address("TestAddress")
			.detailAddress("TestDetailAddress")
			.build();

		signUpRequestDto.encodingPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
		MemberInfoResponseDto memberInfoResponseDto = MemberInfoResponseDto.of(signUpRequestDto.toEntity());

		when(memberService.signUp(any(SignUpRequestDto.class))).thenReturn(memberInfoResponseDto);
		ResponseEntity<MemberInfoResponseDto> responseEntity = memberController.signUp(signUpRequestDto);

		verify(memberService, times(1)).signUp(signUpRequestDto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(memberInfoResponseDto.getEmail(), Objects.requireNonNull(responseEntity.getBody()).getEmail());
	}

	@Test
	@DisplayName("올바르지 않은 이메일 형식은 회원가입을 진행할 수 없다.")
	void signUpTest_invalidEmail() {

		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.name("TestName")
			.email("test@example.com")
			.password("password")
			.tel("1234567890")
			.zipCode("12345")
			.address("TestAddress")
			.detailAddress("TestDetailAddress")
			.build();
		signUpRequestDto.encodingPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

		when(memberService.signUp(any(SignUpRequestDto.class)))
			.thenThrow(new IllegalArgumentException("이메일 형식이 올바르지 않습니다."));

		assertThrows(IllegalArgumentException.class, () -> memberController.signUp(signUpRequestDto));

		verify(memberService, times(1)).signUp(signUpRequestDto);
	}

	@Test
	@DisplayName("중복된 이름(닉네임)은 회원가입 할 수 없다.")
	void checkNameTest() {

		String testName = "TestName";
		Boolean nameDuplicated = true;

		when(memberService.isNameDuplicated(testName)).thenReturn(nameDuplicated);

		ResponseEntity<Boolean> responseEntity = memberController.checkName(testName);

		verify(memberService, times(1)).isNameDuplicated(testName);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals(nameDuplicated, responseEntity.getBody());
	}

}
