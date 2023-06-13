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
import org.springframework.web.bind.annotation.*;

import static com.petlink.member.dto.Message.AVAILABLE_NAME;
import static com.petlink.member.dto.Message.DUPLICATED_NAME;

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
    public ResponseEntity<ResultResponse> checkName(@PathVariable String name) {
        Boolean aBoolean = memberService.isNameDuplicated(name);
        Message code = Boolean.TRUE.equals(aBoolean) ? DUPLICATED_NAME : AVAILABLE_NAME;
        ResultResponse resultResponse = new ResultResponse(aBoolean, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }

    @GetMapping("/withdrawal")
    public ResponseEntity<ResultResponse> withDrawal(String token) {
        Boolean aBoolean = memberService.withDrawal(token);
        Message code = Boolean.TRUE.equals(aBoolean) ? Message.WITHDRAWAL_SUCCESS : Message.WITHDRAWAL_FAIL;
        ResultResponse resultResponse = new ResultResponse(aBoolean, code);

        return new ResponseEntity<>(resultResponse, code.getHttpStatus());
    }
}
