package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exceptions.JwtNotFoundException;

@RestControllerAdvice
public class RatingExceptionHandling extends ResponseEntityExceptionHandler{

	@ExceptionHandler(JwtNotFoundException.class)
	public ResponseEntity<ExceptionModel> handleJwtNotFoundException(JwtNotFoundException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}
	
}
