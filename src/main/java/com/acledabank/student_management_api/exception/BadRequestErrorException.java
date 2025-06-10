package com.acledabank.student_management_api.exception;

public class BadRequestErrorException extends RuntimeException {
    public BadRequestErrorException(String message) {
        super(message);
    }
}

