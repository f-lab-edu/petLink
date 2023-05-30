package com.petlink.funding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.petlink.funding.domain.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {
	Page<Funding> findAll(Pageable pageable);

}
