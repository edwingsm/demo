package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExists  extends ApplicationFailureException{

    public ResourceAlreadyExists(String message,Throwable throwable){
        super(message,throwable);
    }

    @Override
    public HttpStatus getExceptionCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
