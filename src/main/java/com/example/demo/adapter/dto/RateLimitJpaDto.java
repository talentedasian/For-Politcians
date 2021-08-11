package com.example.demo.adapter.dto;

import com.example.demo.domain.entities.RateLimit;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class RateLimitJpaDto{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String accountNumber, politicianNumber;

	@Column(nullable = false)
	private int daysLeft;

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public RateLimitDto(int daysLeft, String accountNumber, String politicianNumber) {
		super();
		this.daysLeft = daysLeft;
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}

	public static RateLimitDto of(RateLimit rateLimit) {
		return new RateLimitDto(rateLimit.daysLeftOfBeingRateLimited(), rateLimit.id(), rateLimit.politicianNumber());
	}

	public RateLimit toRateLimit() {
		LocalDate dateCreated = (Integer.signum(daysLeft) == -1) ? LocalDate.now().plusDays(7).minusDays(daysLeft) : LocalDate.now().minusDays(7).plusDays(daysLeft);

		return new RateLimit(id.toString(), politicianNumber, dateCreated);
	}

}
