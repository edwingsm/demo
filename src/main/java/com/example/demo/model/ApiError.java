package com.example.demo.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import java.util.Date;

@Data
public class ApiError {

    private String path;
    private final Date date =new Date();
    private String message;
    private HttpStatus httpStatus;
            
}
