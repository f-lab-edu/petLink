package com.petlink.member.dto.response;

import com.petlink.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String name;
    private String email;
    private String zipCode;
    private String address;
    private String detailAddress;

    public static UserInfoResponseDto of(Member member) {
        return UserInfoResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}

