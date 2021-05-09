package com.example.demo.apiExceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExceptionModel {

	private String err,code;
	
	private String Optional;

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOptional() {
		return Optional;
	}

	public void setOptional(String optional) {
		Optional = optional;
	}

	public ExceptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionModel(String err, String code, String optional) {
		super();
		this.err = err;
		this.code = code;
		Optional = optional;
	}
	
}
