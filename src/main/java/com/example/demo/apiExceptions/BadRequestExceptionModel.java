package com.example.demo.apiExceptions;

import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BadRequestExceptionModel extends ExceptionModel{

	@JsonIgnore
	private List<FieldError> FieldErrors;
	
	private List<String> message;

	@JsonIgnore
	public List<FieldError> getFieldErrors() {
		return FieldErrors;
	}

	protected void setFieldErrors(List<FieldError> FieldErrors) {
		this.FieldErrors = FieldErrors;
	}

	public List<String> getMessage() {
		return message;
	}

	protected void setMessage(List<String> message) {
		this.message = message;
	}

	public BadRequestExceptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(String err, String code, String optional) {
		super(err, code, optional);
		// TODO Auto-generated constructor stub
	}

	public BadRequestExceptionModel(List<FieldError> FieldErrors, List<String> message) {
		super();
		this.FieldErrors = FieldErrors;
		this.message = message;
	}
	
	public BadRequestExceptionModel(String err, String code, String optional,
			List<FieldError> FieldErrors, List<String> message) {
		super(err, code, optional);
		this.FieldErrors = FieldErrors;
		this.message = message;
	}

	
	
}
