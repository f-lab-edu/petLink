package com.petlink.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
			.csrf().disable()
			.formLogin().disable()
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/").permitAll()
					.requestMatchers(HttpMethod.POST, "/petlink/members/signup").permitAll()
					.requestMatchers(HttpMethod.GET, "/petlink/members/duplicate/{name}").permitAll()
					.requestMatchers(HttpMethod.GET, "/petlink/members/login").permitAll()
					.anyRequest().authenticated()
			)
			.authenticationProvider(authenticationProvider);
		return http.build();
	}
}
