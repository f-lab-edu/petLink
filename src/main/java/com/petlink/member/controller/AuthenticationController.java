package com.petlink.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.member.dto.request.LoginRequest;
import com.petlink.member.dto.request.LogoutRequest;
import com.petlink.member.dto.response.LoginResponse;
import com.petlink.member.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@ModelAttribute @Valid LoginRequest loginRequest) {
		String token = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
		return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
	}

	@PostMapping("/logOut")
	public ResponseEntity<HttpStatus> logOut(@ModelAttribute @Valid LogoutRequest logoutRequest) {
		authenticationService.logout(logoutRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
