package com.example.demo.adapter.dto;

import com.example.demo.domain.entities.RateLimit;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RateLimitJpaDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String accountNumber, politicianNumber;

	@Column(nullable = false)
	private int daysLeft;

	public Long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	protected RateLimitJpaDto() {}

	public RateLimitJpaDto(int daysLeft, String accountNumber, String politicianNumber) {
		super();
		this.daysLeft = daysLeft;
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}

	public RateLimitJpaDto(String accountNumber, String politicianNumber) {
		this.accountNumber = accountNumber;
		this.politicianNumber = politicianNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RateLimitJpaDto that = (RateLimitJpaDto) o;

		if (daysLeft != that.daysLeft) return false;
		if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
			return false;
		return politicianNumber != null ? politicianNumber.equals(that.politicianNumber) : that.politicianNumber == null;
	}

	@Override
	public int hashCode() {
		int result = accountNumber != null ? accountNumber.hashCode() : 0;
		result = 31 * result + (politicianNumber != null ? politicianNumber.hashCode() : 0);
		result = 31 * result + daysLeft;
		return result;
	}

	@Override
	public String toString() {
		return "RateLimitJpaDto{" +
				"id=" + id +
				", accountNumber='" + accountNumber + '\'' +
				", politicianNumber='" + politicianNumber + '\'' +
				", daysLeft=" + daysLeft +
				'}';
	}

	public static RateLimitJpaDto of(RateLimit rateLimit) {
		return new RateLimitJpaDto(rateLimit.daysLeftOfBeingRateLimited(), rateLimit.id(), rateLimit.politicianNumber());
	}

	public RateLimit toRateLimit() {
		LocalDate dateCreated = (Integer.signum(daysLeft) == -1) ? LocalDate.now().plusDays(7).minusDays(daysLeft) : LocalDate.now().minusDays(7).plusDays(daysLeft);

		return new RateLimit(accountNumber, politicianNumber, dateCreated);
	}

}
