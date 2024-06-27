package com.petlink.order.funding.repository;

import com.petlink.order.funding.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long>, CustomFundingRepository {
}
