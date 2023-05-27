package com.petlink.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.member.domain.Member;
import com.petlink.member.exception.NotFoundMemberException;
import com.petlink.member.exception.NotMatchedPasswordException;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthenticationService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	public String login(String email, String password) {
		//회원이 존재하지 않을 경우 예외 처리
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundMemberException("회원을 찾을 수 없습니다."));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new NotMatchedPasswordException("비밀번호가 일치하지 않습니다.");
		}

		return jwtTokenProvider.createToken(member);
	}

}
