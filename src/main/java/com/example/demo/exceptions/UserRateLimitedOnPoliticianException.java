package com.example.demo.exceptions;

public class UserRateLimitedOnPoliticianException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long daysLeft;

	public Long getDaysLeft() {
		return daysLeft;
	}

	public UserRateLimitedOnPoliticianException(Long daysLeft) {
		super(String.format("User is rate limited and can rate again after %s days", daysLeft));
		this.daysLeft = daysLeft;
		// TODO Auto-generated constructor stub
	}
	
}
