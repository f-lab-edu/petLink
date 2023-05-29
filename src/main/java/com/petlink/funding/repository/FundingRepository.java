package com.petlink.funding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petlink.funding.domain.Funding;
import com.petlink.funding.dto.response.FundingListDto;

public interface FundingRepository extends JpaRepository<Funding, Long> {
	@Query("SELECT new com.petlink.funding.dto.response.FundingListDto(f.id, f.title, f.miniTitle) FROM Funding f")
	List<FundingListDto> findAllFundingSummaries();
}
