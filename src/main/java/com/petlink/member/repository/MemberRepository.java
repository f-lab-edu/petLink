package com.petlink.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petlink.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
