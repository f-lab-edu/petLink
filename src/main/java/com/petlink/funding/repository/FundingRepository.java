package com.petlink.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petlink.funding.domain.Funding;

public interface FundingRepository extends JpaRepository<Funding, Long> {

}
