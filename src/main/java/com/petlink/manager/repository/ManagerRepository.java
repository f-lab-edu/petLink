package com.petlink.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petlink.funding.domain.Funding;

public interface ManagerRepository extends JpaRepository<Funding, Long> {
}
