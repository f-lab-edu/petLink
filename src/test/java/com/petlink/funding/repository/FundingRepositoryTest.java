package com.petlink.funding.repository;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.petlink.funding.domain.Funding;
import com.petlink.manager.repository.ManagerRepository;

@DataJpaTest
class FundingRepositoryTest {

	@Autowired
	FundingRepository fundingRepository;
	@Autowired
	ManagerRepository managerRepository;

	@Test
	@DisplayName("펀딩 목록을 조회할 수 있다.")
	void findAll() {
		List<Funding> fundingList = fundingRepository.findAll();
		fundingList.forEach(System.out::println);
	}
}
