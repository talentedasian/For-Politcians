package com.example.demo.domain.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RateLimit {

	private transient static final Map<String, RateLimit> cache = new HashMap<>();

	private String id;

	private String politicianNumber;

	private ExpirationDate expirationDate;

	public String id() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String politicianNumber() {
		return politicianNumber;
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}

	public LocalDate expirationDate() {
		return expirationDate.expirationDate();
	}

	public RateLimit(String id, String politicianNumber, LocalDate expirationDate) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.expirationDate = new ExpirationDate(expirationDate);
	}

	public RateLimit(String id, String politicianNumber, ExpirationDate expirationDate) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.expirationDate = expirationDate;
	}

	public static RateLimit withNotExpiredRateLimit(String id, String politicianNumber) {
		return cache.computeIfAbsent(id, (key) -> {
			var rateLimit = new RateLimit(id, politicianNumber, LocalDate.now().minusDays(8));

			return rateLimit;
		});
	};

	public RateLimit() {
		super();
	}

	@Override
	public String toString() {
		return "RateLimit [id=" + id + ", politicianNumber=" + politicianNumber + "]";
	}

	public boolean isNotRateLimited() {
		return expirationDate.isNotRateLimited();
	}

	public Integer daysLeftOfBeingRateLimited() {
		return Integer.valueOf(expirationDate.daysLeftTillRateLimited());
	}

}
