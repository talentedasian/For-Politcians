package com.example.demo.exceptions;

public class RateLimitedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long secondsOfTimeout;

	public Long getSecondsOfTimeout() {
		return secondsOfTimeout;
	}

	public RateLimitedException(Long timeout) {
		super();
		this.secondsOfTimeout = timeout;
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Long timeout) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.secondsOfTimeout = timeout;
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message, Throwable cause, Long timeout) {
		super(message, cause);
		this.secondsOfTimeout = timeout;
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(String message, Long timeout) {
		super(message);
		this.secondsOfTimeout = timeout;
		// TODO Auto-generated constructor stub
	}

	public RateLimitedException(Throwable cause, Long timeout) {
		super(cause);
		this.secondsOfTimeout = timeout;
		// TODO Auto-generated constructor stub
	}
	
}
