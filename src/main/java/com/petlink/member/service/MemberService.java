package com.petlink.member.service;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public MemberInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {

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

    public Boolean isNameDuplicated(String name) {
        return memberRepository.existsByName(name);
    }

    public Boolean withdrawal(String token) {
        Long id = tokenProvider.getIdByToken(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION));
        return member.withdrawal();
    }
}
