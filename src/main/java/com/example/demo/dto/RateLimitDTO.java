package com.example.demo.dto;

import org.springframework.hateoas.RepresentationModel;

public class RateLimitDTO extends RepresentationModel<RateLimitDTO>{

	private final String daysLeft, accountNumber, politicianNumber;

	public String getDaysLeft() {
		return daysLeft;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}


	public RateLimitDTO(String daysLeft, String accountNumber, String politicianNumber) {
		super();
		this.daysLeft = daysLeft;
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}
	
}
