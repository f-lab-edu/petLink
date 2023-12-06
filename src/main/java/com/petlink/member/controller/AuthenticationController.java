package com.petlink.member.controller;

import com.petlink.common.cache.TokenCacheService;
import com.petlink.member.dto.request.LoginRequest;
import com.petlink.member.dto.response.LoginResponse;
import com.petlink.member.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenCacheService tokenCacheService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest,
                                               HttpServletResponse response) {
        String token = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
        response.setHeader("Authorization", "Bearer " + token);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            log.info("add blacklist token : " + token);
            tokenCacheService.addBlackList(token);
        }
        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 문자열을 추출
        }
        return null;
    }
}
