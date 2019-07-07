package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ApplicationFailureException extends Exception implements ExceptionCode{



    public ApplicationFailureException(String message,Throwable cause){
        super(message,cause);
    }

    public ApplicationFailureException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getExceptionCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
