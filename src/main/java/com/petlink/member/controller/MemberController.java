package com.petlink.member.controller;

import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.dto.response.ResultResponse;
import com.petlink.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ResultResponse resultResponse = new ResultResponse(aBoolean);

        return new ResponseEntity<>(
                resultResponse,
                Boolean.TRUE.equals(aBoolean) ? HttpStatus.OK : HttpStatus.CONFLICT
        );
    }
}
