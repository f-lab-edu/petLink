package com.petlink.member.service;

import org.springframework.stereotype.Service;

import com.petlink.common.encrypt.EncryptHelper;
import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.UserInfoResponseDto;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final EncryptHelper encryptHelper;

	public UserInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {
		Member member = signUpRequestDto.toEntity(encryptHelper);
		memberRepository.save(member);
		return UserInfoResponseDto.of(member);
	}
}
