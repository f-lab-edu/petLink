package com.petlink.user.member.repository;

import com.petlink.user.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByName(String name);

    Boolean existsByEmail(String email);

    Optional<Member> findByName(String username);

    Optional<Member> findByEmail(String memberEmail);
}
