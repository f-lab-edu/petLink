package com.petlink.config.jpa;

import com.petlink.config.security.customUser.CustomUserDetails;
import com.petlink.manager.domain.Manager;
import com.petlink.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JpaAuditingConfigurationTest {

    private JpaAuditingConfiguration.SpringSecurityAuditorAwareImpl auditorAware;

    @BeforeEach
    public void setUp() {
        auditorAware = new JpaAuditingConfiguration.SpringSecurityAuditorAwareImpl();
    }

    @Test
    @DisplayName("멤버일 때 현재 멤버 정보 가져오기")
    public void testGetCurrentAuditorForMember() {
        // Given
        Member member = Member.builder().email("email@google.com").build();
        CustomUserDetails userDetails = CustomUserDetails.builder().user(member).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Authentication 객체의 principal을 가져옵니다.
        Object principal = authentication.getPrincipal();

        // Then
        assertNotNull(principal);
    }


    @Test
    @DisplayName("매니저일 때 현재 감사자 정보 가져오기")
    public void testGetCurrentAuditorForManager() {
        // Given
        CustomUserDetails userDetails = new CustomUserDetails(Manager.builder().email("manager@petlink.co.kr").build());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Authentication 객체의 principal을 가져옵니다.
        Object principal = authentication.getPrincipal();

        // Then
        assertNotNull(principal);
    }

    @Test
    @DisplayName("사용자가 없을 때 빈 옵셔널 반환하기")
    public void testGetCurrentAuditorForNoUser() {
        // Given
        SecurityContextHolder.clearContext();

        // When
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

        // Then
        assertFalse(currentAuditor.isPresent());
    }

}