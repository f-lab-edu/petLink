package com.petlink.member.service;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.member.domain.Member;
import com.petlink.member.exception.MemberException;
import com.petlink.member.exception.MemberExceptionCode;
import com.petlink.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberException(MemberExceptionCode.NOT_MATCHED_INFOMATION);
        }

        return jwtTokenProvider.createToken(member);
    }

}
