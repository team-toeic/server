package com.comeontoeic.exception.custom;

public class AlreadyRegisteredUserException extends CustomException {
    public AlreadyRegisteredUserException() {
        super(ErrorCode.ALREADY_REGISTERED_USER_EXCEPTION);
    }
}
