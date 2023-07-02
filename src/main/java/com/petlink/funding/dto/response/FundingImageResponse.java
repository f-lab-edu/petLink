package com.petlink.funding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FundingImageResponse {
    String link;
    String name;
    LocalDateTime uploadedAt;
}
