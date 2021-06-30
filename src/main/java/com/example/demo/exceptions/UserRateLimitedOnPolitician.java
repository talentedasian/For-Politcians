package com.example.demo.exceptions;

public class UserRateLimitedOnPolitician extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Long daysLeft;

	public Long getDaysLeft() {
		return daysLeft;
	}

	public UserRateLimitedOnPolitician(Long daysLeft) {
		super();
		this.daysLeft = daysLeft;
		// TODO Auto-generated constructor stub
	}

	public UserRateLimitedOnPolitician(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Long daysLeft) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.daysLeft = daysLeft;
		// TODO Auto-generated constructor stub
	}

	public UserRateLimitedOnPolitician(String message, Throwable cause, Long daysLeft) {
		super(message, cause);
		this.daysLeft = daysLeft;
		// TODO Auto-generated constructor stub
	}

	public UserRateLimitedOnPolitician(String message, Long daysLeft) {
		super(message);
		this.daysLeft = daysLeft;
		// TODO Auto-generated constructor stub
	}

	public UserRateLimitedOnPolitician(Throwable cause) {
		super(cause);
		this.daysLeft = null;
		// TODO Auto-generated constructor stub
	}

}
