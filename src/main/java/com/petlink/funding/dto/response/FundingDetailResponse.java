package com.petlink.funding.dto.response;

import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FundingDetailResponse {
    private Long id;
    private Long managerId;
    private String managerName;
    private String managerEmail;
    private String phoneNumber;
    private String title;
    private String miniTitle;
    private String content;
    private FundingState state;
    private FundingCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long targetDonation;
    private Long successDonation;
}
