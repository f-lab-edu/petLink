package com.petlink.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.petlink.member.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

	private static String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	@ExceptionHandler(value = {MemberException.class})
	public ResponseEntity<ErrorResponse> handleGenericException(MemberException exception) {
		String currentTime = getCurrentTime();
		log.error("\n [ Member Service error ] {} : {}", currentTime, exception.getMessage());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(exception.getMessage())
			.status(exception.getHttpStatus())
			.build();

		return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	}
}
