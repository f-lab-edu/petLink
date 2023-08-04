package com.petlink.member.controller;

import com.petlink.common.util.jwt.JwtToken;
import com.petlink.member.dto.request.LoginRequest;
import com.petlink.member.dto.response.LoginResponse;
import com.petlink.member.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest,
                                               HttpServletResponse response) {

        String token = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());

        Cookie cookie = new Cookie(JwtToken.JWT_TOKEN.getTokenName(), token);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie(JwtToken.JWT_TOKEN.getTokenName(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
