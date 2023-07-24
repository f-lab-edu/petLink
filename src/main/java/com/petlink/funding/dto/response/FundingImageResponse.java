package com.petlink.funding.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FundingImageResponse {
    private Long id;
    private String link;
    private String name;
    private LocalDateTime uploadedAt;
}
