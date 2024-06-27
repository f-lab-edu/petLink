package com.petlink.global.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()  // CSRF 공격 방지 필터 비활성화
				.addFilter(corsFilter()) // CORS 필터 추가
				.formLogin().disable() // 시큐리티 기본 로그인 페이지 사용 안함
				.httpBasic().disable()  // http 기본 인증 비활성화
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.build();
	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 응답시 json을 자바스크립트에서 처리 가능 여부
		config.addAllowedOriginPattern("*"); // 모든 ip에 대해 응답을 허용
		config.addAllowedHeader("*");  // 모든 header에 응답을 허용
		config.addAllowedMethod("*");  // 모든 post, get, put, delete, patch 요청을 허용
		source.registerCorsConfiguration("/**", config); // 모든 url에 대해 위의 설정을 적용
		return new CorsFilter(source);
	}
	/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()  // CSRF 공격 방지 필터 비활성화
                .addFilter(corsConfig.corsFilter()) // CORS 필터 추가
                .formLogin().disable() // 시큐리티 기본 로그인 페이지 사용 안함
                .httpBasic().disable()  // http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 세션 생성 정책 설정: STATELESS (상태 정보를 서버에 저장하지 않음)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(POST, "/fundings/manage/**").hasAuthority(MANAGER.getRole()) // 펀딩 관리자 권한이 필요한 요청
                        .requestMatchers(POST, "/fundings/**").authenticated() // 펀딩 생성 권한이 필요한 요청
                        .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) // JWT 인증 필터에서 발생하는 예외 처리
                .and().authenticationProvider(authenticationProvider) // 커스텀 인증 프로바이더 추가
                .build();
    }   */
}