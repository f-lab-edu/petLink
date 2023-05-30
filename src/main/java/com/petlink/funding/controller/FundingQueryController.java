package com.petlink.funding.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.funding.dto.response.FundingInfoDto;
import com.petlink.funding.dto.response.FundingSummaryDto;
import com.petlink.funding.service.FundingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fundings/queries")
@RequiredArgsConstructor
public class FundingQueryController {

	private final FundingService fundingService;

	@GetMapping
	public ResponseEntity<Page<FundingSummaryDto>> getFundingSummaries(Pageable pageable) {
		return ResponseEntity.ok(fundingService.getFundingSummaries(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<FundingInfoDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(fundingService.findById(id));
	}
}

