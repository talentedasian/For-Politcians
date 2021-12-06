package com.example.demo.exceptions;

public class PoliticianNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoliticianNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoliticianNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PoliticianNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PoliticianNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public static PoliticianNotFoundException withPolNumber(String polNumber) {
		return new PoliticianNotFoundException(String.format("Politician with %s as politician number does not exist", polNumber));
	}

	public PoliticianNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
