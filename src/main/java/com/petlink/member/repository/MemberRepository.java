package com.petlink.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petlink.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
