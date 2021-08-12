package com.example.demo.domain.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RateLimit {

	private transient static final Map<String, RateLimit> cache = new HashMap<>();
	
	static {
		/*
		 * Commonly used for testing
		 */
		var rateLimit = new RateLimit();
		rateLimit.setId("TGFLM-00000000000123");
		rateLimit.setPoliticianNumber("123polNumber");
		
		cache.put(rateLimit.id(), rateLimit);
	}

	private String id;

	private String politicianNumber;

	private LocalDate dateCreated;

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

	public RateLimit(String id, String politicianNumber, LocalDate dateCreated) {
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.dateCreated = dateCreated;
	}
	
	public static RateLimit withNotExpiredRateLimit(String id, String politicianNumber) {
		return cache.computeIfAbsent(id, (key) -> {
			var rateLimit = new RateLimit();
			rateLimit.setId(id);
			rateLimit.setPoliticianNumber(politicianNumber);
			
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
		return this.dateCreated == null || LocalDate.now().minusDays(7).isBefore(dateCreated);
	}

	public Integer daysLeftOfBeingRateLimited() {
		return determineDaysLeftToRate(dateCreated);
	}

	public static int determineDaysLeftToRate(LocalDate date) {
		return LocalDate.now().minusDays(7).getDayOfMonth() - date.getDayOfMonth();
	}

}
