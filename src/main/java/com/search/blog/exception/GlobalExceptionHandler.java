package com.search.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(BindException.class)
  protected ResponseEntity<?> handlerBindException(BindException ex) {
    String errorType = "InvalidArgument";
    String errorMsg = ex.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    Map<String, Object> errorResult = new HashMap<>();

    errorResult.put("errorType", errorType);
    errorResult.put("message", errorMsg);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorResult);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<?> handlerException(Exception ex) {
    log.error("unhandled exception: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Internal Server Error");
  }
}
