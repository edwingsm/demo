package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    HttpStatus getExceptionCode();
}
