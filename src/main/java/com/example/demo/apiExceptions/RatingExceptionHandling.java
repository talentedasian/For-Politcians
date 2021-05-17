package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.controller.RatingsController;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtNotFromServerException;
import com.example.demo.exceptions.SwaggerJWTException;

import io.jsonwebtoken.ExpiredJwtException;

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
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtMalformedException(ExpiredJwtException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(JwtNotFromServerException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleJwtMalformedException(JwtNotFromServerException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return exceptionModel;
	}
	
	@ExceptionHandler(SwaggerJWTException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionModel handleMisuseOfSwaggerJWTException(SwaggerJWTException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		exceptionModel.setOptional("Don't even think about it");
		
		return exceptionModel;
	}
	
}
