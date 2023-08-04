package com.petlink.funding.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FundingExceptionCode {

    //존재하지 않는 펀딩 번호입니다.
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 펀딩 번호입니다."),
    FUNDING_ALREADY_CLOSED(HttpStatus.BAD_REQUEST, "펀딩이 종료되었습니다."),
    FUNDING_ALREADY_SUCCESS(HttpStatus.BAD_REQUEST, "펀딩이 성공되었습니다."),
    FUNDING_ALREADY_FAIL(HttpStatus.BAD_REQUEST, "펀딩이 실패되었습니다."),
    FUNDING_ALREADY_CANCEL(HttpStatus.BAD_REQUEST, "펀딩이 취소되었습니다."),
    NO_SEARCH_RESULTS_FOUND(HttpStatus.NOT_FOUND, "검색 결과가 없습니다."),
    INVALID_CATEGORY_VALUES(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리 값입니다  : "),
    INVALID_STATE_VALUES(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 값입니다  : ");

    private final HttpStatus httpStatus;
    private final String message;
}
