package com.petlink.order.orders.repository;

import com.petlink.order.orders.domain.FundingItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdersRepository extends JpaRepository<FundingItemOrder, Long> {

}
