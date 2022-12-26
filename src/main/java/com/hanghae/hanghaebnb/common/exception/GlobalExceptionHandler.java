package com.hanghae.hanghaebnb.common.exception;

import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex){
        return new ResponseEntity(new ResponseDto(ex.getErrorCode().getStatusCode(), ex.getErrorCode().getMsg(), null), HttpStatus.OK );
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handlerServerException(Exception ex){
        return new ResponseEntity(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
