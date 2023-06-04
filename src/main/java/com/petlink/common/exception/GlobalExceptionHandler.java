package com.petlink.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.petlink.funding.exception.FundingException;
import com.petlink.member.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

	private static String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	private ResponseEntity<ErrorResponse> buildAndReturnResponse(HttpStatus status, String message) {
		String currentTime = getCurrentTime();
		log.error("\n [ Error ] {} : {}", currentTime, message);

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(message)
			.status(status)
			.build();

		return ResponseEntity.status(status).body(errorResponse);
	}

	@ExceptionHandler(value = {MemberException.class})
	public ResponseEntity<ErrorResponse> handleGenericException(MemberException exception) {
		return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
	}

	@ExceptionHandler(value = {FundingException.class})
	public ResponseEntity<ErrorResponse> handleFundingException(FundingException exception) {
		return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();
		String fieldName = Objects.requireNonNull(fieldError).getField();
		Object rejectedValue = fieldError.getRejectedValue();
		String errorMessage = "유효하지 않은 파라미터입니다: " + fieldName + " [" + rejectedValue + "]";

		return buildAndReturnResponse(HttpStatus.BAD_REQUEST, errorMessage);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		String fieldName = exception.getName();
		String rejectedValue = Objects.requireNonNull(exception.getValue()).toString();
		String errorMessage = "유효하지 않은 파라미터입니다: " + fieldName + " [" + rejectedValue + "]";

		return buildAndReturnResponse(HttpStatus.BAD_REQUEST, errorMessage);
	}
}
