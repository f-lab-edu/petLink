package com.petlink.funding.dto.response;

import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingListResponseDto {
    private Long id;
    private String title;
    private FundingState state;
    private FundingCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
