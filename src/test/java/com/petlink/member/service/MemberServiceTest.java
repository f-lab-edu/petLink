package com.petlink.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService 테스트")
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입을 진행할 수 있다.")
	void testSignUp() {
		// Given
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.name("TestName")
			.email("test@example.com")
			.password("password")
			.tel("1234567890")
			.zipCode("12345")
			.address("TestAddress")
			.detailAddress("TestDetailAddress")
			.build();

		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(memberRepository.existsByEmail(anyString())).thenReturn(false);
		when(memberRepository.save(any(Member.class))).thenAnswer(i -> i.getArguments()[0]);

		// When
		MemberInfoResponseDto result = memberService.signUp(signUpRequestDto);

		// Then
		assertNotNull(result);
		assertEquals(signUpRequestDto.getName(), result.getName());
		assertEquals(signUpRequestDto.getEmail(), result.getEmail());
		verify(memberRepository, times(1)).existsByEmail(signUpRequestDto.getEmail());
		verify(memberRepository, times(1)).save(any(Member.class));
	}
}
