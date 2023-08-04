package com.petlink.member.dto.response;

import com.petlink.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseDto {
    private Long id;
    private String name;
    private String email;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

}

