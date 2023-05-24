package com.petlink.member.service;

import static com.petlink.common.util.JwtTokenUtils.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petlink.member.domain.Member;
import com.petlink.member.exception.NotFoundMemberException;
import com.petlink.member.exception.NotMatchedPasswordException;
import com.petlink.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.expire-length}")
	private Long expireLength;

	public String login(String email, String password) {
		//회원이 존재하지 않을 경우 예외 처리
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundMemberException("회원을 찾을 수 없습니다."));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new NotMatchedPasswordException("비밀번호가 일치하지 않습니다.");
		}

		return createToken(member.getEmail(), secretKey, expireLength);
	}
}
