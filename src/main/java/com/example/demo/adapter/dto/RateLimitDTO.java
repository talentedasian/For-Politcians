package com.example.demo.adapter.dto;

import com.example.demo.domain.entities.RateLimit;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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

	public static RateLimitDTO of(RateLimit rateLimit) {
		return new RateLimitDTO(rateLimit.daysLeftOfBeingRateLimited().toString(), rateLimit.getId(), rateLimit.getPoliticianNumber());
	}

	public RateLimit toRateLimit() {
		return (daysLeft.isEmpty() | daysLeft.isBlank() | daysLeft == null) ?
			new RateLimit(accountNumber, politicianNumber, null) :
			new RateLimit(accountNumber, politicianNumber, LocalDate.now().minusDays(7L).plusDays(Long.valueOf(daysLeft)));
	}


}
