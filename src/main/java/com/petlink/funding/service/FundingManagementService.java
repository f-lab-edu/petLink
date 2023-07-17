package com.petlink.funding.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.dto.response.FundingCreateResponse;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.manager.domain.Manager;
import com.petlink.manager.exception.ManagerException;
import com.petlink.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.petlink.manager.exception.ManagerExceptionCode.MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FundingManagementService {
    private final FundingRepository fundingRepository;
    private final ManagerRepository managerRepository;
    private final double rate = 0.8;

    @Transactional
    public FundingCreateResponse createFunding(FundingPostDto fundingPostDto) {
        Manager manager = managerRepository
                .findById(fundingPostDto.getManagerId())
                .orElseThrow(() -> new ManagerException(MANAGER_NOT_FOUND));

        Funding funding =
                fundingRepository.save(Funding.builder()
                        .manager(manager)
                        .title(fundingPostDto.getTitle())
                        .miniTitle(fundingPostDto.getMiniTitle())
                        .content(fundingPostDto.getContent())
                        .state(FundingState.SCHEDULED)
                        .category(fundingPostDto.getCategory())
                        .startDate(fundingPostDto.getStartDate())
                        .endDate(fundingPostDto.getEndDate())
                        .targetDonation(fundingPostDto.getTargetDonation())
                        .successDonation(calculateSuccessDonation(fundingPostDto.getTargetDonation()))
                        .build());

        return FundingCreateResponse.builder()
                .id(funding.getId())
                .registeredAt(funding.getCreatedDate())
                .build();
    }

    private Long calculateSuccessDonation(Long targetDonation) {
        return Math.round(targetDonation * rate);
    }
}
