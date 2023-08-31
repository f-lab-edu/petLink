package com.petlink.funding.repository;

import com.petlink.funding.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long>, CustomFundingRepository {
}
