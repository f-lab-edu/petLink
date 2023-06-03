package com.petlink.funding.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;

public interface CustomFundingRepository {

	Slice<Funding> findFundingList(LocalDateTime startDate, LocalDateTime endDate, List<FundingCategory> categories,
		List<FundingState> states, Pageable pageable);
}
