package com.petlink.config.jpa;

import com.petlink.config.security.customUser.CustomUserDetails;
import jakarta.validation.constraints.NotNull;
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

    public static class SpringSecurityAuditorAwareImpl implements AuditorAware<String> {
        @NotNull
        @Override
        public Optional<String> getCurrentAuditor() {
            // 현재 보안 컨텍스트에서 Authentication 객체를 가져옵니다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 인증이 없거나 인증되지 않았다면 Optional.empty()를 반환합니다.
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            // Authentication 객체의 principal을 가져옵니다.
            Object principal = authentication.getPrincipal();

            // principal이 CustomUserDetails의 인스턴스인지 확인합니다.
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;

                String email = null;
                // Manager가 null이 아니라면 Manager의 이메일을 가져옵니다.
                if (userDetails.getManager() != null) {
                    email = userDetails.getManager().getEmail();
                }
                // Manager가 null이고 Member가 null이 아니라면 Member의 이메일을 가져옵니다.
                else if (userDetails.getMember() != null) {
                    email = userDetails.getMember().getEmail();
                }
                // 이메일이 null이면 Optional.empty()를 반환하고, 그렇지 않으면 이메일을 포함하는 Optional을 반환합니다.
                return Optional.ofNullable(email);
            }
            // principal이 CustomUserDetails의 인스턴스가 아니라면 principal을 문자열로 캐스팅하고 이를 포함하는 Optional을 반환합니다.
            else {
                return Optional.of((String) principal);
            }
        }
    }
}
