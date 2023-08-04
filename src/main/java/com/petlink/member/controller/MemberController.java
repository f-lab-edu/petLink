package com.petlink.member.controller;

import com.petlink.member.dto.Message;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.dto.response.ResultResponse;
import com.petlink.member.service.MemberService;
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

import static com.petlink.member.dto.Message.AVAILABLE_NAME;
import static com.petlink.member.dto.Message.DUPLICATED_NAME;
import static com.petlink.member.dto.Message.WITHDRAWAL_SUCCESS;

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
        Message code = Boolean.TRUE.equals(aBoolean) ? DUPLICATED_NAME : AVAILABLE_NAME;
        ResultResponse resultResponse = new ResultResponse(aBoolean, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<ResultResponse> withdrawal(@CookieValue("token") String token) {
        memberService.withdrawal(token);
        Message code = WITHDRAWAL_SUCCESS;
        ResultResponse resultResponse = new ResultResponse(true, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }
}
