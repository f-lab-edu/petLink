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
import com.petlink.member.dto.response.UserInfoResponseDto;
import com.petlink.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/petlink/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<UserInfoResponseDto> signUp(@ModelAttribute SignUpRequestDto signUpRequestDto) {
		UserInfoResponseDto responseDto = memberService.signUp(signUpRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@GetMapping("/duplicate/{name}")
	public ResponseEntity<Boolean> checkName(@PathVariable String name) {
		return new ResponseEntity<>(memberService.isNameDuplicated(name), HttpStatus.OK);
	}

}
