package com.petlink.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.member.dto.request.LoginRequest;
import com.petlink.member.dto.response.LoginResponse;
import com.petlink.member.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@ModelAttribute @Valid LoginRequest loginRequest) {
		String token = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());

		return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
	}

}
