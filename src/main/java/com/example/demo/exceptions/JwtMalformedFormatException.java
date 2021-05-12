package com.example.demo.exceptions;

public class JwtMalformedFormatException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtMalformedFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtMalformedFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public JwtMalformedFormatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public JwtMalformedFormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public JwtMalformedFormatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
