package com.co.planeador.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String message;
    private static final String GENERIC_ERROR_MESSAGE = "Ha ocurrido un error inesperado";
    private static final HttpStatus GENERIC_ERROR_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus CONTROLLED_ERROR_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public CustomException(){
        this(GENERIC_ERROR_HTTP_STATUS, GENERIC_ERROR_MESSAGE);
    }

    public CustomException(String message){
        this(CONTROLLED_ERROR_HTTP_STATUS, message);
    }

    public CustomException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
