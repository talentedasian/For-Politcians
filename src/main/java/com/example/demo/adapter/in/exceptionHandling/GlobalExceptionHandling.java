package com.example.demo.adapter.in.exceptionHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler{

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> messages = new ArrayList<>();
		for (FieldError errors : ex.getFieldErrors()) {
			messages.add("Error on " + errors.getField() + ". " + errors.getDefaultMessage());
		}
		
		var exceptionModel = new BadRequestExceptionModel(ex.getFieldErrors(), messages);
		exceptionModel.setCode("400");
		exceptionModel.setErr("Bad Request in one of the fields");
		exceptionModel.setOptional("Property names must be snake case. E.g. from firstName to first_name");
		return new ResponseEntity<Object>(exceptionModel, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		var exceptionModel = new BadRequestExceptionModel();
		exceptionModel.setCode("400");
		exceptionModel.setErr("Bad Request on path variable");
		exceptionModel.setOptional("Error on " + ex.getParameter());

		return new ResponseEntity<Object>(exceptionModel, HttpStatus.BAD_REQUEST);
	}
}
