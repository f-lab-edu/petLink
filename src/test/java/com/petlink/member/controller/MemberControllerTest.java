package com.petlink.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.petlink.common.encrypt.EncryptHelper;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.UserInfoResponseDto;
import com.petlink.member.service.MemberService;

class MemberControllerTest {

	@InjectMocks
	private MemberController memberController;

	@Mock
	private MemberService memberService;

	@Mock
	private EncryptHelper encryptHelper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("회원가입 테스트")
	void signUpTest() {

		SignUpRequestDto signUpRequestDto = new SignUpRequestDto(
			"TestName",
			"test@example.com",
			"password",
			"1234567890",
			"12345",
			"TestAddress",
			"TestDetailAddress"
		);

		UserInfoResponseDto userInfoResponseDto = UserInfoResponseDto.of(signUpRequestDto.toEntity(encryptHelper));

		when(memberService.signUp(any(SignUpRequestDto.class))).thenReturn(userInfoResponseDto);
		ResponseEntity<UserInfoResponseDto> responseEntity = memberController.signUp(signUpRequestDto);

		verify(memberService, times(1)).signUp(signUpRequestDto);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(userInfoResponseDto, responseEntity.getBody());
	}

	@Test
	@DisplayName("중복된 이름 확인 테스트")
	void checkNameTest() {

		String testName = "TestName";
		Boolean nameDuplicated = true;

		when(memberService.isNameDuplicated(testName)).thenReturn(nameDuplicated);

		ResponseEntity<Boolean> responseEntity = memberController.checkName(testName);

		verify(memberService, times(1)).isNameDuplicated(testName);
		assertEquals(HttpStatus.CONFLICT,
			responseEntity.getStatusCode());
		assertEquals(nameDuplicated, responseEntity.getBody());
	}

}