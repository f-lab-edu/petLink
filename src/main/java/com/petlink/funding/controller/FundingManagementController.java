package com.petlink.funding.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.dto.response.FundingCreateResponse;
import com.petlink.funding.dto.response.FundingImageResponse;
import com.petlink.funding.service.FundingManagementService;
import com.petlink.image.dto.ImageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/fundings/manage")
@RequiredArgsConstructor
public class FundingManagementController {

    private final FundingManagementService managementService;


    //펀딩 등록 A - 게시글 작성
    @PostMapping("/create")
    public ResponseEntity<FundingCreateResponse> createFunding(@RequestBody @Valid FundingPostDto fundingPostDto) {
        Long funding = managementService.createFunding(fundingPostDto);
        return ResponseEntity.ok(FundingCreateResponse
                .builder()
                .id(funding)
                .registeredAt(LocalDateTime.now())
                .build());
    }

    //펀딩 등록 B - 단건 이미지 업로드
    @PostMapping("/image/upload")
    public ResponseEntity<FundingImageResponse> uploadImage(@ModelAttribute @Valid ImageDto imageDto) throws AmazonS3Exception, IOException {
        return ResponseEntity.ok(managementService.uploadImage(imageDto));
    }


    //펀딩 등록 C - reward 추가
    @PostMapping("reward/add/{funding_id}")
    public ResponseEntity<Object> addRewards(@PathVariable("funding_id") Long fundingId) {
        return ResponseEntity.ok("리워드 등록");
    }

}

