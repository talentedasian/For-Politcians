package com.example.demo.dto;

public class RateLimitDto {

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


	public RateLimitDto(String daysLeft, String accountNumber, String politicianNumber) {
		super();
		this.daysLeft = daysLeft;
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}
	
}
