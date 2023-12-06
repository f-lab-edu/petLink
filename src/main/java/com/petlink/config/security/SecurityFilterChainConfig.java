package com.petlink.config.security;

import com.petlink.config.filter.JwtAuthenticationEntryPoint;
import com.petlink.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.petlink.common.util.jwt.UserRole.MANAGER;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider; // 커스텀 인증 프로바이더(유저 정보를 DB에서 가져오는 역할)
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWT 인증 필터
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // JWT 인증 필터에서 발생하는 예외 처리
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()  // CSRF 공격 방지 필터 비활성화
                .addFilter(corsConfig.corsFilter()) // CORS 필터 추가
                .formLogin().disable() // 시큐리티 기본 로그인 페이지 사용 안함
                .httpBasic().disable()  // http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 세션 생성 정책 설정: STATELESS (상태 정보를 서버에 저장하지 않음)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(POST, "/fundings/manage/**").hasAuthority(MANAGER.getRole()) // 펀딩 관리자 권한이 필요한 요청
                        .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) // JWT 인증 필터에서 발생하는 예외 처리
                .and().authenticationProvider(authenticationProvider) // 커스텀 인증 프로바이더 추가
                .build();
    }
}