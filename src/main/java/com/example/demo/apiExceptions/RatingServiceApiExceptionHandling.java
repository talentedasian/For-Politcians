package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.controller.RatingsController;
import com.example.demo.exceptions.RatingsNotFoundException;

@RestControllerAdvice(assignableTypes = { RatingsController.class })
public class RatingServiceApiExceptionHandling {

	@ExceptionHandler(RatingsNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionModel handleRatingsNotFoundException(RatingsNotFoundException ex) {
		ExceptionModel exceptionModel = new ExceptionModel();
		exceptionModel.setCode("404");
		exceptionModel.setErr(ex.getMessage());
		
		return exceptionModel;
	}
	
}
