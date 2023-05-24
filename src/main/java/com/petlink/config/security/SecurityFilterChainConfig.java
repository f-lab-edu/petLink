package com.petlink.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.formLogin()
			.disable()
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers(HttpMethod.GET, "/").permitAll()
					.requestMatchers(HttpMethod.GET, "/members/duplicate/{name}").permitAll()
					.requestMatchers(HttpMethod.POST, "/members/signup").permitAll()
					.requestMatchers("/auth/login").permitAll()
					.requestMatchers("/auth/logout").permitAll()
					.anyRequest().authenticated()
			)
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 정책 설정: STATELESS (상태 정보를 서버에 저장하지 않음)
			.and()
			.authenticationProvider(authenticationProvider);
		return http.build();
	}

}
