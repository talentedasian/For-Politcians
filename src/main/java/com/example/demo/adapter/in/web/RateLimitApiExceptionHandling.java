package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.exceptionHandling.ExceptionModel;
import com.example.demo.exceptions.RateLimitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RateLimitApiExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RateLimitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleRateLimitNotFoundException(RateLimitNotFoundException e) {
        var exceptionModel = new ExceptionModel();
        exceptionModel.setCode("404");
        exceptionModel.setErr(e.getMessage());

        return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
    }

}
