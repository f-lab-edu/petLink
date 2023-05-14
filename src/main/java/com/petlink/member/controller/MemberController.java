package com.petlink.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	@GetMapping("/member/{id}")
	public String getMember(@PathVariable Long id) {
		return "member";
	}
}
