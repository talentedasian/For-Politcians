package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.PoliticianNumber;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RateLimitJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String accountNumber, politicianNumber;

	@Column(nullable = false)
	private LocalDate dateCreated;

	public Long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	@ExcludeFromJacocoGeneratedCoverage
	RateLimitJpaEntity() {}

	protected RateLimitJpaEntity(LocalDate dateCreated, String accountNumber, String politicianNumber) {
		super();
		this.dateCreated = dateCreated;
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}

	public RateLimitJpaEntity(String accountNumber, String politicianNumber) {
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "RateLimitJpaEntity{" +
				"id=" + id +
				", accountNumber='" + accountNumber + '\'' +
				", politicianNumber='" + politicianNumber + '\'' +
				", dateCreated=" + dateCreated +
				'}';
	}

	public static RateLimitJpaEntity of(RateLimit rateLimit) {
		return new RateLimitJpaEntity(rateLimit.dateCreated(), rateLimit.id(), rateLimit.politicianNumber());
	}

	public RateLimit toRateLimit() {
		return new RateLimit(accountNumber, new PoliticianNumber(politicianNumber), dateCreated);
	}

}
