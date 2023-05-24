package com.petlink.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.UserInfoResponseDto;
import com.petlink.member.exception.AlreadyRegisteredMemberException;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public UserInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {

		Member member = signUpRequestDto.toEntity();
		signUpRequestDto.encodingPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

		Boolean emailDuplicated = memberRepository.existsByEmail(signUpRequestDto.getEmail());
		if (Boolean.TRUE.equals(emailDuplicated)) {
			throw new AlreadyRegisteredMemberException();
		}

		memberRepository.save(member);
		return UserInfoResponseDto.of(member);
	}

	public Boolean isNameDuplicated(String name) {
		return memberRepository.existsByName(name);
	}
}
