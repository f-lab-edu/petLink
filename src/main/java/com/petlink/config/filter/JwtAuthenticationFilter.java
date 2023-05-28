package com.petlink.config.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.petlink.common.util.jwt.JwtToken;
import com.petlink.common.util.jwt.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final List<String> excludedPaths = List.of("/members/duplicate/{name}", "/members/signup", "/auth/login");

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain) throws ServletException, IOException {

		if (excludedPaths.contains(request.getServletPath())) {
			filterChain.doFilter(request, response);
			return;
		}

		log.info("JWT Filtering....");

		String token = parseJwtFromCookie(request);
		log.info("memberEmail: {}", jwtTokenProvider.getEmailByToken(token));
		if (StringUtils.hasText(token) && jwtTokenProvider.isTokenValid(token)) {
			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwtFromCookie(HttpServletRequest request) {
		return Optional.ofNullable(request.getCookies())
			.stream()
			.flatMap(Arrays::stream)
			.filter(cookie -> JwtToken.JWT_TOKEN.getTokenName().equals(cookie.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElse(null);
	}

	private Authentication getAuthentication(String token) {
		String memberEmail = jwtTokenProvider.getEmailByToken(token);
		log.info("memberEmail: {}", memberEmail);
		return new UsernamePasswordAuthenticationToken(memberEmail, null, new ArrayList<>());
	}
}
