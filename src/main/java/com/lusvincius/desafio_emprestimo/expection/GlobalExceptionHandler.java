package com.lusvincius.desafio_emprestimo.expection;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandling(MethodArgumentNotValidException exception, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
        LocalDateTime.now(),
        String.format("[%s] %s",
            exception.getBindingResult().getObjectName(),
            exception.getBindingResult().getFieldError().getDefaultMessage()
        ),
        request.getDescription(false),
        BAD_REQUEST.value()
    );

    return ResponseEntity.status(BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> globalExceptionHandling(Exception exception, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
        LocalDateTime.now(),
        exception.getMessage(),
        request.getDescription(false),
        INTERNAL_SERVER_ERROR.value()
    );
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorDetails);
  }
}
