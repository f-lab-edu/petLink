package com.petlink.funding.item.repository;

import com.petlink.funding.item.domain.FundingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<FundingItem, Long> {
}
