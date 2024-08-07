package com.petlink.api.storage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StorageExceptionCode {

    NOT_FOUND_IMAGE_FILE(HttpStatus.NOT_FOUND, "이미지 파일이 존재하지 않습니다."),
    NOT_FOUND_FILE_NAME(HttpStatus.NOT_FOUND, "파일 이름이 존재하지 않습니다."),
    NOT_FOUND_IMAGE_LINK(HttpStatus.NOT_FOUND, "이미지 경로가 없습니다."),
    FAILED_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
