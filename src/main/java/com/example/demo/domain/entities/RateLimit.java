package com.example.demo.domain.entities;

import com.example.demo.domain.ExpirationZonedDate;

import java.time.LocalDate;
import java.util.Objects;

public class RateLimit {

	public static final long RATE_LIMIT = 7;

	private String id;

	private PoliticianNumber politicianNumber;

	private ExpirationZonedDate expirationZonedDate;

	public String id() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String politicianNumber() {
		return politicianNumber.politicianNumber();
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = new PoliticianNumber(politicianNumber);
	}

	public LocalDate expirationDate() {
		return expirationZonedDate.expirationDate(RATE_LIMIT);
	}

	public RateLimit (String id, PoliticianNumber politicianNumber, ExpirationZonedDate expirationDate) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.expirationZonedDate = expirationDate;
	}

	public RateLimit() {
		super();
	}

	@Override
	public String toString() {
		return "RateLimit { " +
				"id='" + id +
				", politicianNumber=" + politicianNumber +
				", expirationZonedDate=" + expirationZonedDate +
				" } ";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RateLimit rateLimit = (RateLimit) o;

		if (!Objects.equals(id, rateLimit.id)) return false;
		if (!Objects.equals(politicianNumber, rateLimit.politicianNumber))
			return false;
		return Objects.equals(expirationZonedDate, rateLimit.expirationZonedDate);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (politicianNumber != null ? politicianNumber.hashCode() : 0);
		result = 31 * result + (expirationZonedDate != null ? expirationZonedDate.hashCode() : 0);
		return result;
	}

	public boolean isNotRateLimited() {
		return expirationZonedDate == null || expirationZonedDate.isExpired(RATE_LIMIT);
	}

	public Integer daysLeftOfBeingRateLimited() {
		return Integer.valueOf(expirationZonedDate.daysLeftTillExpiration(RATE_LIMIT));
	}

	public LocalDate dateCreated() {
		return expirationZonedDate.dateCreated();
	}

}
