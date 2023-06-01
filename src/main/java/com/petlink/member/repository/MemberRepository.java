package com.petlink.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petlink.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Boolean existsByName(String name);

	Boolean existsByEmail(String email);

	Optional<Member> findByName(String username);

	Optional<Member> findByEmail(String memberEmail);
}
