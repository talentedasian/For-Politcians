package com.example.demo.apiExceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BadRequestExceptionModel extends ExceptionModel{

	@JsonIgnore
	protected List<ObjectError> objectErrors;
	
	protected List<String> message;

	public BadRequestExceptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(String err, String code, String optional) {
		super(err, code, optional);
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(List<ObjectError> objectErrors, List<String> message) {
		super();
		this.objectErrors = objectErrors;
		this.message = message;
	}
	
	public BadRequestExceptionModel(String err, String code, String optional,
			List<ObjectError> objectErrors, List<String> message) {
		super(err, code, optional);
		this.objectErrors = objectErrors;
		this.message = message;
	}

	
	
}
