package com.petlink.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.exception.MemberException;
import com.petlink.member.exception.MemberExceptionCode;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {

		signUpRequestDto.encodingPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
		Member member = signUpRequestDto.toEntity();

		Boolean emailDuplicated = memberRepository.existsByEmail(signUpRequestDto.getEmail());
		if (Boolean.TRUE.equals(emailDuplicated)) {
			throw new MemberException(MemberExceptionCode.ALREADY_REGISTERED_MEMBER);
		}

		memberRepository.save(member);
		return MemberInfoResponseDto.of(member);
	}

	public Boolean isNameDuplicated(String name) {
		return memberRepository.existsByName(name);
	}
}
