package com.example.demo.exceptions;

public class RateLimitNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RateLimitNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RateLimitNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RateLimitNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RateLimitNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RateLimitNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
