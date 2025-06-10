package com.acledabank.student_management_api.exception;

public class NotFoundErrorException extends RuntimeException {

    public NotFoundErrorException(String message) {
        super(message);
    }
}
