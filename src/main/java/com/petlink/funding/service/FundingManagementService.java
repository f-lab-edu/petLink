package com.petlink.funding.service;

import com.petlink.common.storage.dto.ResultObject;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.dto.response.FundingCreateResponse;
import com.petlink.funding.dto.response.FundingImageResponse;
import com.petlink.funding.item.repository.ItemRepository;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.image.domain.Image;
import com.petlink.image.dto.ImageDto;
import com.petlink.image.service.ImageService;
import com.petlink.manager.domain.Manager;
import com.petlink.manager.exception.ManagerException;
import com.petlink.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;
import static com.petlink.manager.exception.ManagerExceptionCode.MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FundingManagementService {
    private final ItemRepository itemRepository;
    private final FundingRepository fundingRepository;
    private final ManagerRepository managerRepository;
    private final double rate = 0.8;
    private final ImageService imageService;

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
                        .startDate(toLocalDateTime(fundingPostDto.getStartDate()))
                        .endDate(toLocalDateTime(fundingPostDto.getEndDate()))
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

    @Transactional
    public FundingImageResponse uploadImage(ImageDto imageDto) throws IOException {
        //이미지를 오브젝트 스토리지에 업로드
        ResultObject resultObject = imageService.uploadToImageServer(imageDto);
        //이미지 정보를 DB에 저장
        Image image = imageService.saveImageInfo(resultObject);
        //이미지 정보를 응답
        return FundingImageResponse
                .builder()
                .id(image.getId())
                .link(resultObject.getImageLink())
                .name(resultObject.getObjectName())
                .uploadedAt(image.getCreatedDate())
                .build();
    }

}
