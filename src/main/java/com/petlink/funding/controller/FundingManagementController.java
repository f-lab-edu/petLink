package com.petlink.funding.controller;

import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.service.FundingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fundings/manage")
@RequiredArgsConstructor
public class FundingManagementController {

    private final FundingService fundingService;


    //펀딩 등록 A - 게시글 작성
    @PostMapping("/create")
    public ResponseEntity<Object> createFunding(@RequestBody @Valid FundingPostDto fundingPostDto) {
        // 펀딩 게시글 등록 처리
        // 이미지 처리는 게시글과 별개로 동시에 진행된다.
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }

    //펀딩 등록 B - 이미지 추가
    @PostMapping("/images/add")
    public ResponseEntity<Object> addImage(MultipartFile image) {
        // 이미지 업로드 처리
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }
    //펀딩 등록 C - reward 추가

}

