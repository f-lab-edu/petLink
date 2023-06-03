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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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
	private final List<String> excludedPaths = List.of("/members/duplicate/**", "/members/signup", "/auth/login",
		"/fundings", "/fundings/**");
	private final PathMatcher pathMatcher = new AntPathMatcher();

	private static void generateTokenExceptionMessage(HttpServletResponse response, String message) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.getWriter().println("{ \"message\": \"" + message + "\" }");
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

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain) throws ServletException, IOException {

		boolean isExcluded = excludedPaths.stream()
			.anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

		if (isExcluded) {
			filterChain.doFilter(request, response);
			return;
		}

		log.info("JWT Filtering....");

		String token = parseJwtFromCookie(request);

		if (token == null) {
			generateTokenExceptionMessage(response, "인증을 위해 JWT 토큰이 필요합니다.");
			filterChain.doFilter(request, response);
			return;
		}

		if (!jwtTokenProvider.isTokenValid(token)) {
			generateTokenExceptionMessage(response, "유효하지 않은 토큰입니다.");
			filterChain.doFilter(request, response);
			return;
		}

		if (StringUtils.hasText(token) && jwtTokenProvider.isTokenValid(token)) {

			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
