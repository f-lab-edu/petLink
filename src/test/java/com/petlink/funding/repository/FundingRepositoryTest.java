package com.petlink.funding.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.petlink.config.jpa.QuerydslConfiguration;
import com.petlink.funding.domain.Funding;
import com.petlink.funding.domain.FundingCategory;
import com.petlink.funding.domain.FundingState;

@Import(QuerydslConfiguration.class)
@DataJpaTest
class FundingRepositoryTest {

	@Autowired
	FundingRepository fundingRepository;

	@Test
	@DisplayName("펀딩 목록을 조회할 수 있다.")
	void findAll() {
		LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2023, 12, 1, 0, 0, 0);

		Slice<Funding> fundingList = fundingRepository.findFundingList(
			startDate,
			endDate,
			List.of(FundingCategory.values()),
			List.of(FundingState.values()),
			PageRequest.of(0, 2)
		);

		fundingList.forEach(System.out::println);
		Assertions.assertEquals(2, fundingList.getContent().size());
	}
}
