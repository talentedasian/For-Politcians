package com.example.demo.exceptions;

public class JwtNotFromServerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtNotFromServerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtNotFromServerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFromServerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFromServerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public JwtNotFromServerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
