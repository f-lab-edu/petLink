package com.petlink.order.funding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FundingCreateResponse {
    Long id;
    LocalDateTime registeredAt;
}
