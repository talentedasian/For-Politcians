package com.example.demo.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.controller.RatingsController;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.JwtTamperedExpcetion;

@RestControllerAdvice(assignableTypes = { RatingsController.class })
public class JwtProvidersExceptionHandling extends ResponseEntityExceptionHandler{

	@ExceptionHandler(JwtNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ExceptionModel> handleNoJwtFoundOnHeaderException() {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr("Accessing protected resource. No jwt found on authorization Header");
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(JwtTamperedExpcetion.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ExceptionModel> handleJwtTamperedException(JwtTamperedExpcetion e) {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr(e.getMessage());
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}
	
}
