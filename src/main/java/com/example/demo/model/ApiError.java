package com.example.demo.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {

    private final Date date =new Date();
    private HttpStatus httpStatus;
    private String message;
    private String path;



            
}
