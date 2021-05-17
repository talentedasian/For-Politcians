package com.example.demo.exceptions;

import io.jsonwebtoken.Claims;

public class RefreshTokenException extends RuntimeException {

	private Claims claims;
	
	public Claims getClaims() {
		return claims;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RefreshTokenException(Claims claims) {
		this.claims = claims;
		
	}

	public RefreshTokenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RefreshTokenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RefreshTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RefreshTokenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
