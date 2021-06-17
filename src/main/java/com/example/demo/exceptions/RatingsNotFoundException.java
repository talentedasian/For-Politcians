package com.example.demo.exceptions;

public class RatingsNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RatingsNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingsNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RatingsNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RatingsNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RatingsNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
