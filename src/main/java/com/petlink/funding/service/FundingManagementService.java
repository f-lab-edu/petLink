package com.petlink.funding.service;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.manager.domain.Manager;
import com.petlink.manager.exception.ManagerException;
import com.petlink.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import static com.petlink.manager.exception.ManagerExceptionCode.MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FundingManagementService {
    private final FundingRepository fundingRepository;
    private final ManagerRepository managerRepository;


    public Long createFunding(FundingPostDto fundingPostDto) {
        Manager manager = managerRepository
                .findById(fundingPostDto.getManagerId())
                .orElseThrow(() -> new ManagerException(MANAGER_NOT_FOUND));

        Funding funding =
                fundingRepository.save(Funding.builder()
                        .manager(manager)
                        .title(fundingPostDto.getTitle())
                        .miniTitle(fundingPostDto.getMiniTitle())
                        .content(
                                HtmlUtils.htmlEscape(fundingPostDto.getContent())
                        )
                        .state(FundingState.SCHEDULED)
                        .category(fundingPostDto.getCategory())
                        .startDate(fundingPostDto.getStartDate())
                        .endDate(fundingPostDto.getEndDate())
                        .targetDonation(fundingPostDto.getTargetDonation())
                        .successDonation(Math.round(fundingPostDto.getTargetDonation() * 0.8))
                        .build());

        return funding.getId();
    }
}
