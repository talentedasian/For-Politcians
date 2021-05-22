package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.controller.RatingsController;
import com.example.demo.exceptions.PoliticianNotFoundException;
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
	
	@ExceptionHandler(PoliticianNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionModel> handlePoliticianNotFoundException(PoliticianNotFoundException e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("404");
		exceptionModel.setErr(e.getMessage());
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.NOT_FOUND);
	}
	
}
