package com.petlink.config.jpa;

import com.petlink.config.security.customUser.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAwareImpl();
    }

    public class SpringSecurityAuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {

            // Spring Security를 사용하여 현재 사용자를 가져옵니다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            // 여기서는 사용자의 이메일을 반환하고 있습니다.
            // 필요에 따라 사용자 ID나 다른 식별자를 반환할 수 있습니다.
            return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getManager().getEmail());
        }
    }
}
