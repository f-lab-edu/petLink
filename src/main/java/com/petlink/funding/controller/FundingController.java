package com.petlink.funding.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.funding.dto.response.FundingDetailResponseDto;
import com.petlink.funding.dto.response.FundingListDto;
import com.petlink.funding.service.FundingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/funding")
@RequiredArgsConstructor
public class FundingController {

	private final FundingService fundingService;

	@GetMapping("/list")
	public ResponseEntity<List<FundingListDto>> findAllFundingSummaries() {
		return ResponseEntity.ok(fundingService.findAllFundingSummaries());
	}

	@GetMapping("/{id}")
	public ResponseEntity<FundingDetailResponseDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(fundingService.findById(id));
	}
}

