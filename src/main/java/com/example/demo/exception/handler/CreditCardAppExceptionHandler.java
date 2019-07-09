package com.example.demo.exception.handler;

import com.example.demo.controller.AdminController;
import com.example.demo.controller.AuthController;
import com.example.demo.exception.ApplicationFailureException;
import com.example.demo.model.ApiError;
import com.example.demo.repository.dbo.CreditCard;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice(basePackageClasses = {AdminController.class, AuthController.class, CreditCard.class})
public class CreditCardAppExceptionHandler {


  @ExceptionHandler(ApplicationFailureException.class)
  @RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  @ResponseBody
  public ResponseEntity<ApiError> handleInvalidConnectionIdentifierError(
      HttpServletRequest request, ApplicationFailureException ex) {
    final String path = request.getServletPath();
    final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    final String message = ex.getMessage();
    final ApiError rpaApiErrorModel = new ApiError(status, message, path);
    return new ResponseEntity<>(rpaApiErrorModel, status);
  }

}
