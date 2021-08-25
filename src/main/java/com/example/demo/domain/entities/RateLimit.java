package com.example.demo.domain.entities;

import com.example.demo.domain.politicians.PoliticianNumber;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RateLimit {

	private transient static final Map<String, RateLimit> cache = new HashMap<>();

	private String id;

	private PoliticianNumber politicianNumber;

	private ExpirationDate expirationDate;

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
		return expirationDate.expirationDate();
	}

	public RateLimit(String id, PoliticianNumber politicianNumber, LocalDate expirationDate) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.expirationDate = new ExpirationDate(expirationDate);
	}

	public RateLimit(String id, PoliticianNumber politicianNumber, ExpirationDate expirationDate) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.expirationDate = expirationDate;
	}

	public RateLimit() {
		super();
	}

	@Override
	public String toString() {
		return "RateLimit [id=" + id + ", politicianNumber=" + politicianNumber + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RateLimit rateLimit = (RateLimit) o;

		if (!Objects.equals(id, rateLimit.id)) return false;
		if (!Objects.equals(politicianNumber, rateLimit.politicianNumber))
			return false;
		return Objects.equals(expirationDate, rateLimit.expirationDate);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (politicianNumber != null ? politicianNumber.hashCode() : 0);
		result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
		return result;
	}

	public boolean isNotRateLimited() {
		return expirationDate == null ? true : expirationDate.isNotRateLimited();
	}

	public Integer daysLeftOfBeingRateLimited() {
		return Integer.valueOf(expirationDate.daysLeftTillRateLimited());
	}

	public LocalDate dateCreated() {
		return expirationDate.dateCreated();
	}

}
