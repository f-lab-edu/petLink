package com.petlink.user.member.controller;

import com.petlink.user.member.dto.Message;
import com.petlink.user.member.dto.request.SignUpRequestDto;
import com.petlink.user.member.dto.response.MemberInfoResponseDto;
import com.petlink.user.member.dto.response.ResultResponse;
import com.petlink.user.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberInfoResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        MemberInfoResponseDto responseDto = memberService.signUp(signUpRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/duplicate/{name}")
    public ResponseEntity<ResultResponse> verifyNameExistence(@PathVariable String name) {
        Boolean aBoolean = memberService.isNameDuplicated(name);
        Message code = Boolean.TRUE.equals(aBoolean) ? Message.DUPLICATED_NAME : Message.AVAILABLE_NAME;
        ResultResponse resultResponse = new ResultResponse(aBoolean, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<ResultResponse> withdrawal(@CookieValue("token") String token) {
        memberService.withdrawal(token);
        Message code = Message.WITHDRAWAL_SUCCESS;
        ResultResponse resultResponse = new ResultResponse(true, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }
}
