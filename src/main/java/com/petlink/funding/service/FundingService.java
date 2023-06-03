package com.petlink.funding.service;

import static com.petlink.funding.exception.FundingExceptionCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.petlink.funding.dto.request.FundingListRequestDto;
import com.petlink.funding.dto.response.DetailInfoResponse;
import com.petlink.funding.dto.response.FundingListDto;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.repository.FundingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FundingService {
	private final FundingRepository fundingRepository;

	public Page<FundingListDto> getFundingList(FundingListRequestDto requestDto, Pageable pageable) {

		return null;
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
