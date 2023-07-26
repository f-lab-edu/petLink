package com.petlink.member.dto.response;

import com.petlink.member.dto.Message;
import lombok.Getter;

@Getter
public class ResultResponse {
    String message;
    Message code;
    Boolean result;

    public ResultResponse(Boolean result, Message code) {
        this.result = result;
        this.code = code;
        this.message = this.code.getMessage();
    }
}
