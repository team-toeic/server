package com.comeontoeic.exception.custom;

public class PasswordNotMatchException extends CustomException {
    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH_EXCEPTION);
    }
}
