package com.petlink.member.dto.response;

import com.petlink.member.dto.Message;
import lombok.Getter;

@Getter
public class ResultResponse {
    String message;
    Message code;
    Boolean result;

    public ResultResponse(Boolean result) {
        this.result = result;
        this.code = Boolean.TRUE.equals(result) ? Message.DUPLICATED_NAME : Message.AVAILABLE_NAME;
        this.message = this.code.getMessage();
    }
}
