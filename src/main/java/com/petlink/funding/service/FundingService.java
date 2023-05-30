package com.petlink.funding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.petlink.common.exception.ExceptionCode;
import com.petlink.common.exception.GlobalException;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.response.FundingDetailResponseDto;
import com.petlink.funding.dto.response.FundingListDto;
import com.petlink.funding.repository.FundingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundingService {
	private final FundingRepository fundingRepository;

	public List<FundingListDto> findAllFundingSummaries() {
		return fundingRepository.findAllFundingSummaries();
	}

	public FundingDetailResponseDto findById(Long id) {
		Funding funding = fundingRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ExceptionCode.FUNDING_NOT_FOUND));

		return FundingDetailResponseDto.builder()
			.id(funding.getId())
			.managerId(funding.getManager().getId())
			.managerName(funding.getManager().getName())
			.managerEmail(funding.getManager().getEmail())
			.phoneNumber(funding.getManager().getPhoneNumber())
			.title(funding.getTitle())
			.miniTitle(funding.getMiniTitle())
			.content(funding.getContent())
			.state(funding.getState())
			.category(funding.getCategory())
			.startDate(funding.getStartDate())
			.endDate(funding.getEndDate())
			.targetDonation(funding.getTargetDonation())
			.successDonation(funding.getSuccessDonation())
			.build();
	}

}
