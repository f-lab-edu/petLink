package com.petlink.common.storage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StorageExceptionCode {

    NOT_NULL_IMAGE_FILE(HttpStatus.BAD_REQUEST, "이미지 파일이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
