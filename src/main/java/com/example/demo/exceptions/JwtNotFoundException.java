package com.example.demo.exceptions;

public class JwtNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
