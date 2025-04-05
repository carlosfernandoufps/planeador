package com.co.planeador.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex){
        CustomException customException = new CustomException();
        return new ResponseEntity<>(customException.getMessage(), customException.getHttpStatus());
    }
*/
}
