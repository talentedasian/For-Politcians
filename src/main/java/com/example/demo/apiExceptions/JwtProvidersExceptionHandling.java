package com.example.demo.apiExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.jwt.JwtProvider;

@RestControllerAdvice(assignableTypes = { JwtProvider.class, JwtProvidersExceptionHandling.class })
public class JwtProvidersExceptionHandling extends ResponseEntityExceptionHandler{

	@ExceptionHandler(JwtNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ExceptionModel> handleNoJwtFoundOnHeaderException() {
		var exceptionModel = new ExceptionModel();
		exceptionModel.setCode("401");
		exceptionModel.setErr("Accessing protected resource. No jwt found on authorization Header");
		
		return new ResponseEntity<ExceptionModel>(exceptionModel, HttpStatus.UNAUTHORIZED);
	}
}
