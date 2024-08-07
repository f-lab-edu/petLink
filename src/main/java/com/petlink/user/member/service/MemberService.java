package com.petlink.user.member.service;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.global.exception.TokenException;
import com.petlink.user.member.domain.InvalidEmail;
import com.petlink.user.member.domain.Member;
import com.petlink.user.member.dto.request.SignUpRequestDto;
import com.petlink.user.member.dto.response.MemberInfoResponseDto;
import com.petlink.user.member.exception.MemberException;
import com.petlink.user.member.exception.MemberExceptionCode;
import com.petlink.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.petlink.global.exception.TokenExceptionCode.INVALID_TOKEN_EXCEPTION;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public MemberInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        checkNotAvailableEmail(signUpRequestDto.getEmail());

        signUpRequestDto.encodingPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        Member member = signUpRequestDto.toEntity();

        Boolean existsByEmail = memberRepository.existsByEmail(signUpRequestDto.getEmail());
        if (Boolean.TRUE.equals(existsByEmail)) {
            throw new MemberException(MemberExceptionCode.ALREADY_REGISTERED_MEMBER);
        }
        Boolean existsByName = memberRepository.existsByName(signUpRequestDto.getName());
        if (Boolean.TRUE.equals(existsByName)) {
            throw new MemberException(MemberExceptionCode.ALREADY_USED_NAME);
        }

        memberRepository.save(member);
        return MemberInfoResponseDto.of(member);
    }

    private void checkNotAvailableEmail(String email) {
        for (InvalidEmail invalidEmail : InvalidEmail.values()) {
            if (email.endsWith(invalidEmail.getEmailSuffix())) {
                throw new MemberException(MemberExceptionCode.NOT_AVAILABLE_EMAIL);
            }
        }
    }

    public Boolean isNameDuplicated(String name) {
        return memberRepository.existsByName(name);
    }

    public void withdrawal(String token) {

        if (tokenProvider.isTokenValid(token)) {
            throw new TokenException(INVALID_TOKEN_EXCEPTION);
        }

        Long id = tokenProvider.getIdByToken(token);
        memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION))
                .withdrawal();
    }
}
