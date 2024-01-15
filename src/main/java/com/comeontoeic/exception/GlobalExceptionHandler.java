package com.comeontoeic.exception;

import com.comeontoeic.exception.custom.CustomException;
import com.comeontoeic.exception.custom.ErrorCode;
import com.comeontoeic.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.warn("handleException = {}", e.getMessage());

        return handleExceptionInternal();
    }

//    @ExceptionHandler(BindException.class)
//    public ResponseEntity<?> bindException(BindException e) {
//        log.warn("bindException", e);
//        return handleExceptionInternal(e);
//    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> customException(CustomException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    private ResponseEntity<?> handleExceptionInternal() {
        return new ResponseEntity<>(makeErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        return new ResponseEntity<>(makeErrorResponse(errorCode), errorCode.getHttpStatus());
    }

    private ResponseEntity<?> handleExceptionInternal(BindException e) {
        return new ResponseEntity<>(makeErrorResponse(e), ErrorCode.BINDING_EXCEPTION.getHttpStatus());
    }

    private ErrorResponse makeErrorResponse() {
        return ErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .code(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse makeErrorResponse(BindException e) {
        List<com.comeontoeic.exception.response.ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .toList();

        return ErrorResponse.builder()
                .httpStatus(ErrorCode.BINDING_EXCEPTION.getHttpStatus())
                .code(ErrorCode.BINDING_EXCEPTION.getHttpStatus().value())
                .message(ErrorCode.BINDING_EXCEPTION.getMessage())
                .errors(validationErrorList)
                .build();
    }
}
