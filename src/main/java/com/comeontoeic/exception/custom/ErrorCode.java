package com.comeontoeic.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    BINDING_EXCEPTION(HttpStatus.BAD_REQUEST, "binding exception"),

    //유저 정보 관련 API
    USERNAME_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "username not found"),
    ALREADY_REGISTERED_USER_EXCEPTION(HttpStatus.BAD_REQUEST, "already registered user"),
    PASSWORD_NOT_MATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "password not match"),
    TERMS_OF_SERVICE_AGREEMENT_NEED_EXCEPTION(HttpStatus.BAD_REQUEST, "terms of service agreement need");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
