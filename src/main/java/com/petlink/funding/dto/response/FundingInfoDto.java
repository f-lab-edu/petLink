package com.petlink.funding.dto.response;

import java.time.LocalDateTime;

import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FundingInfoDto {
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
