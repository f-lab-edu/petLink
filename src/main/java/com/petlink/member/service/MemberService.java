package com.petlink.member.service;

import com.petlink.member.domain.Member;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.UserInfoResponseDto;
import com.petlink.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public UserInfoResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        Member member = signUpRequestDto.toEntity();
        memberRepository.save(member);
        return UserInfoResponseDto.of(member);
    }
}
