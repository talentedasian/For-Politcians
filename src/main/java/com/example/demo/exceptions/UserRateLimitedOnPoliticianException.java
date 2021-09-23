package com.example.demo.exceptions;

import com.example.demo.domain.entities.PoliticianNumber;

public class UserRateLimitedOnPoliticianException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final PoliticianNumber polNumber;
	private final long daysLeft;

	public UserRateLimitedOnPoliticianException(Long daysLeft) {
		super(String.format("User is rate limited and can rate again after %s days", daysLeft));
		// TODO Auto-generated constructor stub
		polNumber = null;
		this.daysLeft = daysLeft;
	}

	public UserRateLimitedOnPoliticianException(Long daysLeft, PoliticianNumber polNumber) {
		super(String.format("User is rate limited and can rate again after %s days", daysLeft));
		// TODO Auto-generated constructor stub
		this.polNumber = polNumber;
		this.daysLeft = daysLeft;
	}

	public Long getDaysLeft() {
		return this.daysLeft;
	}


}
