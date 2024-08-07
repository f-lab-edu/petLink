package com.petlink.global.config.security.filter;

import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.common.util.jwt.UserRole;
import com.petlink.global.cache.TokenCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter /*extends OncePerRequestFilter*/ {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCacheService tokenCacheService;
    private final List<String> excludedPaths = List.of(
            "/docs/index.html",
            "/members/duplicate/**",
            "/members/signup",
            "/auth/login",
            "/fundings/**",
            "/orders/**");
    private final PathMatcher pathMatcher = new AntPathMatcher();

    private static void generateTokenExceptionMessage(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("{ \"message\": \"" + message + "\" }");
    }

    private Optional<String> parseJwt(HttpServletRequest request) {
        return Optional.of(request.getHeader("Authorization").substring(7));
    }

    private Authentication getAuthentication(String token) {
        String memberEmail = jwtTokenProvider.getEmailByToken(token);
        log.info("memberEmail: {}", memberEmail);
        return new UsernamePasswordAuthenticationToken(memberEmail, null, new ArrayList<>());
    }

    //@Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("JWT Filtering....{}", request.getServletPath());

        boolean isExcluded = excludedPaths.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> tokenOptional = parseJwt(request);

        if (tokenOptional.isEmpty()) {
            generateTokenExceptionMessage(response, "인증을 위해 JWT 토큰이 필요합니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenOptional.get();

        if (tokenCacheService.isBlackList(token)) {
            generateTokenExceptionMessage(response, "만료된 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info(jwtTokenProvider.getRoleByToken(token));

        if (Objects.equals(jwtTokenProvider.getRoleByToken(token), UserRole.MANAGER.getRole())) {
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
