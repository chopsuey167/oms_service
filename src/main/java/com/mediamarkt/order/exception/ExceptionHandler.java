package com.mediamarkt.order.exception;

import com.mediamarkt.order.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(exception));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(exception));
  }

  private ErrorDto buildErrorResponse(Exception exception) {
    return ErrorDto.builder().message(exception.getMessage()).build();
  }


}
