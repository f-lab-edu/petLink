package com.petlink.common.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.petlink.member.exception.AlreadyRegisteredMemberException;
import com.petlink.member.exception.NotFoundMemberException;
import com.petlink.member.exception.NotMatchedPasswordException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Map<String, Object> createMessageBody(Exception exception) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", exception.getMessage());
		return body;
	}

	// 기존의 일반적인 예외 처리 메소드
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleGenericException(Exception exception) {
		Map<String, Object> body = createMessageBody(exception);
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//validation 예외 처리 메소드
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());

		List<String> errors = ex.getBindingResult()
			.getAllErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.toList();

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	// NotFoundMemberException 예외 처리 메소드
	@ExceptionHandler(value = {NotFoundMemberException.class})
	public ResponseEntity<Object> handleNotFoundMemberException(NotFoundMemberException ex) {
		Map<String, Object> body = createMessageBody(ex);
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	// NotMatchedPasswordException 예외 처리 메소드
	@ExceptionHandler(value = {NotMatchedPasswordException.class})
	public ResponseEntity<Object> handleNotMatchedPasswordException(NotMatchedPasswordException ex) {
		Map<String, Object> body = createMessageBody(ex);

		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	// AlreadyRegisteredMemberException 예외 처리 메소드
	@ExceptionHandler(value = {AlreadyRegisteredMemberException.class})
	public ResponseEntity<Object> handleAlreadyRegisteredMemberException(AlreadyRegisteredMemberException ex) {
		Map<String, Object> body = createMessageBody(ex);
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}
}
