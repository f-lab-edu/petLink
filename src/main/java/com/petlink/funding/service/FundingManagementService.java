package com.petlink.funding.service;

import com.petlink.common.storage.dto.ResultObject;
import com.petlink.common.storage.dto.UploadObject;
import com.petlink.common.storage.image.Bucket;
import com.petlink.common.storage.image.ImageUtils;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingState;
import com.petlink.funding.domain.Image;
import com.petlink.funding.dto.request.FundingImageDto;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.dto.response.FundingImageResponse;
import com.petlink.funding.repository.FundingRepository;
import com.petlink.funding.repository.ImageRepository;
import com.petlink.manager.domain.Manager;
import com.petlink.manager.exception.ManagerException;
import com.petlink.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;
import static com.petlink.manager.exception.ManagerExceptionCode.MANAGER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FundingManagementService {
    private final FundingRepository fundingRepository;
    private final ManagerRepository managerRepository;
    private final ImageRepository imageRepository;
    private final ImageUtils imageUtils;

    @Transactional
    public Long createFunding(FundingPostDto fundingPostDto) {
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
        return funding.getId();
    }

    private Long calculateSuccessDonation(Long targetDonation) {
        return Math.round(targetDonation * 0.8);
    }

    @Transactional
    public FundingImageResponse uploadImage(FundingImageDto fundingImageDto) throws IOException {

        ResultObject resultObject = imageUtils.uploadImage(UploadObject.builder()
                .objectName(getNameWithFolder(fundingImageDto.getObjectName()))
                .imageFile(fundingImageDto.getImage())
                .bucket(Bucket.IMAGE)
                .build());

        Image image = imageRepository.save(Image.builder()
                .path(resultObject.getImageLink())
                .name(resultObject.getObjectName())
                .description("펀딩 이미지")
                .build());

        return FundingImageResponse
                .builder()
                .id(image.getId())
                .link(resultObject.getImageLink())
                .name(resultObject.getObjectName())
                .uploadedAt(LocalDateTime.now())
                .build();
    }

    private String getNameWithFolder(String fileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM/dd/");
        String toDay = LocalDate.now().format(formatter);
        return toDay + fileName;
    }

}
