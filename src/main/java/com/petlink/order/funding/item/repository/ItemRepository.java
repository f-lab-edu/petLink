package com.petlink.order.funding.item.repository;

import com.petlink.order.funding.item.domain.FundingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<FundingItem, Long> {
}
