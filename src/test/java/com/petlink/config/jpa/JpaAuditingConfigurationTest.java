package com.petlink.config.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JpaAuditingConfigurationTest {

    private JpaAuditingConfiguration.SpringSecurityAuditorAwareImpl auditorAware;

    @BeforeEach
    public void setUp() {
        auditorAware = new JpaAuditingConfiguration.SpringSecurityAuditorAwareImpl();
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