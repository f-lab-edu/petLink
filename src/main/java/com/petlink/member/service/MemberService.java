package com.petlink.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petlink.common.encrypt.EncryptHelper;
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
	private final EncryptHelper encryptHelper;

	public UserInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {
		Member member = signUpRequestDto.toEntity(encryptHelper);

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