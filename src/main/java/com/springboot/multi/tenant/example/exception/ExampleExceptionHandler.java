package com.springboot.multi.tenant.example.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExampleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> anyException(
            Exception ex) {

        ErrorResponse errorDTO = new ErrorResponse();
        errorDTO.setCode("500");
        errorDTO.setMessage(ex.getMessage());

        return ResponseEntity.status(500).body(errorDTO);
    }


}
