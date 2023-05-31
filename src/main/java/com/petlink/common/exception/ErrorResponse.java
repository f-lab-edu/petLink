package com.petlink.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final String message;
	private final HttpStatus status;
	private final LocalDateTime timestamp = LocalDateTime.now();
}
