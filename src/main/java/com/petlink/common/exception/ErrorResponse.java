package com.petlink.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
