package com.petlink.funding.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.funding.dto.request.FundingListRequestDto;
import com.petlink.funding.dto.response.DetailInfoResponse;
import com.petlink.funding.dto.response.FundingListResponseDto;
import com.petlink.funding.service.FundingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
public class FundingQueryController {

	private final FundingService fundingService;

	@GetMapping
	public ResponseEntity<Slice<FundingListResponseDto>> getFundingList(
		@Validated FundingListRequestDto requestDto,
		@PageableDefault(value = 5) Pageable pageable
	) {
		return ResponseEntity.ok(fundingService.getFundingList(requestDto, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetailInfoResponse> getFundingById(@PathVariable Long id) {
		return ResponseEntity.ok(fundingService.findById(id));
	}
}

