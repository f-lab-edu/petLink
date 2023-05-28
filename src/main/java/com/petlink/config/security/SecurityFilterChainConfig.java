package com.petlink.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.petlink.config.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
			.cors().disable()
			.csrf().disable()
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers(HttpMethod.GET, "/members/duplicate/{name}").permitAll()
					.requestMatchers(HttpMethod.POST, "/members/signup").permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.requestMatchers("/**").hasRole("MANAGER") // (추후 개선)MANAGER 역할을 가진 사용자에게만 모든 요청을 허용
					.anyRequest().authenticated()
			)
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 정책 설정: STATELESS (상태 정보를 서버에 저장하지 않음)
			.and()
			.formLogin().disable() // 폼 로그인 비활성화
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 필터 체인 맨 앞에 추가
			.build();
	}

}
