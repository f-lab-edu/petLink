package com.petlink.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<MemberInfoResponseDto> signUp(@ModelAttribute @Valid SignUpRequestDto signUpRequestDto) {
		MemberInfoResponseDto responseDto = memberService.signUp(signUpRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@GetMapping("/duplicate/{name}")
	public ResponseEntity<Boolean> checkName(@PathVariable String name) {
		Boolean nameDuplicated = memberService.isNameDuplicated(name);

		return ResponseEntity
			.status(Boolean.TRUE.equals(nameDuplicated) ? HttpStatus.CONFLICT : HttpStatus.OK)
			.body(nameDuplicated);
	}
}
