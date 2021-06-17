package com.example.demo.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.controller.PoliticianController;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;

@RestControllerAdvice(assignableTypes = { PoliticianController.class })
public class PoliticianExceptionHandling extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(PoliticianAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<ExceptionModel> handlePoliticianAlreadyExistsException() {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("409");
		exceptionModel.setErr("Politician Already exists in the database");
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.CONFLICT);
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
