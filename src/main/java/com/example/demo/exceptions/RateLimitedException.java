package com.example.demo.exceptions;

public class RateLimitedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RateLimitedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
