package com.petlink.config.security.filter;

import com.petlink.common.cache.TokenCacheService;
import com.petlink.common.util.jwt.JwtTokenProvider;
import com.petlink.common.util.jwt.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCacheService tokenCacheService;
    private final List<String> excludedPaths = List.of(
            "/docs/index.html",
            "/members/duplicate/**",
            "/members/signup",
            "/auth/login",
            "/fundings/**",
            "/orders/**",
            "/api/oauth/**");

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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : excludedPaths)
            if (pathMatcher.match(path, request.getServletPath()))
                return true;

        return super.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT Filtering....{}", request.getServletPath());

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

        if (Objects.equals(jwtTokenProvider.getRoleByToken(token), Role.MANAGER.getRole())) {
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
