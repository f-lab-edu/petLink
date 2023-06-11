package com.petlink.funding.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.request.FundingRequestDto;
import com.petlink.funding.dto.request.FundingSearchCriteriaDto;
import com.petlink.funding.dto.response.FundingDetailResponse;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.exception.FundingException;
import com.petlink.funding.repository.FundingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import static com.petlink.funding.exception.FundingExceptionCode.FUNDING_NOT_FOUND;
import static com.petlink.funding.exception.FundingExceptionCode.NO_SEARCH_RESULTS_FOUND;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;

    public Slice<FundingListResponseDto> getFundingList(FundingRequestDto requestDto, Pageable pageable) {

        Slice<Funding> fundingList = fundingRepository.findFundingList(new FundingSearchCriteriaDto(requestDto, pageable));

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

    public FundingDetailResponse findById(Long id) {
        return fundingRepository.findById(id)
                .map(funding -> FundingDetailResponse.builder()
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
