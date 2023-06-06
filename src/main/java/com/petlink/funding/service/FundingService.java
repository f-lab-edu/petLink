package com.petlink.funding.service;

import static com.petlink.common.util.date.DateConverter.*;
import static com.petlink.funding.exception.FundingExceptionCode.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.request.FundingListRequestDto;
import com.petlink.funding.dto.response.DetailInfoResponse;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.repository.FundingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundingService {
	private final FundingRepository fundingRepository;

	public Slice<FundingListResponseDto> getFundingList(FundingListRequestDto requestDto, Pageable pageable) {

		Slice<Funding> fundingList = fundingRepository.findFundingList(
			toLocalDateTime(requestDto.getStartDate()),
			toLocalDateTime(requestDto.getEndDate()),
			requestDto.getCategory(),
			requestDto.getState(),
			pageable);

		if (fundingList.isEmpty()) {
			throw new FundingException(NO_SEARCH_RESULTS_FOUND);
		}

		return fundingList
			.map(this::entityToResponse);
	}

	private FundingListResponseDto entityToResponse(Funding funding) {
		return FundingListResponseDto.builder()
			.id(funding.getId())
			.title(funding.getTitle())
			.state(funding.getState())
			.category(funding.getCategory())
			.startDate(funding.getStartDate())
			.endDate(funding.getEndDate())
			.build();
	}

	public DetailInfoResponse findById(Long id) {
		return fundingRepository.findById(id)
			.map(funding -> DetailInfoResponse.builder()
				.id(funding.getId())
				.managerEmail(funding.getManager().getEmail())
				.managerId(funding.getManager().getId())
				.managerName(funding.getManager().getName())
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
				.build())
			.orElseThrow(() -> new FundingException(FUNDING_NOT_FOUND));
	}

}
