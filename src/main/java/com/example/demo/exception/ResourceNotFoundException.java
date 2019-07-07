package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApplicationFailureException {


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getExceptionCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
