package com.petlink.global.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.petlink.api.storage.exception.StorageException;
import com.petlink.order.funding.exception.FundingException;
import com.petlink.order.orders.exception.OrdersException;
import com.petlink.user.manager.exception.ManagerException;
import com.petlink.user.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    @ExceptionHandler(value = {ManagerException.class})
    public ResponseEntity<ErrorResponse> handleGenericException(ManagerException exception) {
        return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(value = {FundingException.class})
    public ResponseEntity<ErrorResponse> handleFundingException(FundingException exception) {
        return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(value = {TokenException.class})
    public ResponseEntity<ErrorResponse> handleFundingException(TokenException exception) {
        return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(value = {StorageException.class})
    public ResponseEntity<ErrorResponse> handleFundingException(StorageException exception) {
        return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(value = {OrdersException.class})
    public ResponseEntity<ErrorResponse> handleFundingException(OrdersException exception) {
        return buildAndReturnResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(value = {AmazonS3Exception.class})
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(AmazonS3Exception exception) {
        return buildAndReturnResponse(HttpStatus.valueOf(exception.getStatusCode()), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String fieldName = Objects.requireNonNull(fieldError).getField();
        Object rejectedValue = fieldError.getRejectedValue();
        return buildAndReturnResponse(HttpStatus.BAD_REQUEST, getErrorMessage(fieldName, rejectedValue));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String fieldName = exception.getName();
        String rejectedValue = Objects.requireNonNull(exception.getValue()).toString();
        return buildAndReturnResponse(HttpStatus.BAD_REQUEST, getErrorMessage(fieldName, rejectedValue));
    }


    private String getErrorMessage(String fieldName, Object rejectedValue) {
        return "유효하지 않은 파라미터입니다: " + fieldName + " [" + rejectedValue + "]";
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        log.error("Exception occurred", exception);
        return buildAndReturnResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}
