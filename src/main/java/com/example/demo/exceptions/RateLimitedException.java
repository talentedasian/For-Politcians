package com.example.demo.exceptions;

public class RateLimitedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long expiration;
	
	public Long getExpiration() {
		return expiration;
	}

	public RateLimitedException(Long expiration, String message) {
		super(message);
		this.expiration = expiration;
	}
	
	public RateLimitedException(Long expiration) {
		super();
		this.expiration = expiration;
	}
	
}
