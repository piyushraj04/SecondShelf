package com.secondshelf.exception;

import com.secondshelf.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> resourceAlreadyExistsException(ResourceAlreadyExistsException exp){
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CONFLICT.value());
        response.setMessage(exp.getMessage());
        response.setData("Failure");

        return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> notFoundException(NotFoundException exp){
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(exp.getMessage());
        response.setData("Failed");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
