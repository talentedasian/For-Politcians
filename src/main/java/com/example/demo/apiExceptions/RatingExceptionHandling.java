package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.controller.RatingsController;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;

@RestControllerAdvice(assignableTypes = { RatingsController.class })
public class RatingExceptionHandling extends ResponseEntityExceptionHandler{

	@ExceptionHandler(JwtNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtNotFoundException(JwtNotFoundException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(JwtMalformedFormatException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtMalformedException(JwtMalformedFormatException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		System.out.println(e.getMessage() + " tanginamo");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
}
