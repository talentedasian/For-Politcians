package com.example.demo.adapter.in.exceptionHandling;

import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = { RatingsController.class })
public class RatingJwtExceptionHandling extends ResponseEntityExceptionHandler{

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
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(JwtExpiredException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtExpiredException(JwtExpiredException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(JwtTamperedExpcetion.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtTamperedException(JwtTamperedExpcetion e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
}
