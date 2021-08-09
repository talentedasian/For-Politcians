package com.example.demo.domain.entities;

import static java.lang.Integer.valueOf;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RateLimit {
	
	@Transient
	private transient static final Map<String, RateLimit> cache = new HashMap<>();
	
	static {
		/*
		 * Commonly used for testing
		 */
		var rateLimit = new RateLimit();
		rateLimit.setDateCreated(LocalDate.now().minusDays(8));
		rateLimit.setId("TGFLM-00000000000123");
		rateLimit.setPoliticianNumber("123polNumber");
		
		cache.put(rateLimit.getId(), rateLimit);
	}
	
	@Id
	private String id;
	
	@Column(nullable = false, name = "politician_number")
	private String politicianNumber;
	
	@Column(nullable = true, name = "date_created")
	private LocalDate dateCreated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public RateLimit(String id, String politicianNumber) {
		super();
		this.id = id;
		this.politicianNumber = politicianNumber;
		this.dateCreated = LocalDate.now();
	}
	
	public static RateLimit withNotExpiredRateLimit(String id, String politicianNumber) {
		return cache.computeIfAbsent(id, (key) -> {
			var rateLimit = new RateLimit();
			rateLimit.setId(id);
			rateLimit.setPoliticianNumber(politicianNumber);
			rateLimit.setDateCreated(LocalDate.now().minusDays(8));
			
			return rateLimit;
		});
	};

	public RateLimit() {
		super();
	}

	@Override
	public String toString() {
		return "RateLimit [id=" + id + ", politicianNumber=" + politicianNumber + ", dateCreated=" + dateCreated + "]";
	}
	
	public boolean isNotRateLimited() {
		return this.dateCreated == null || (LocalDate.now().minusDays(7L).isAfter(dateCreated) | 
				LocalDate.now().minusDays(7L).isEqual(dateCreated));
	}
	
	// return null if user is not rate limited
	public Integer daysLeftOfBeingRateLimited() {
		if (isNotRateLimited()) {
			return null;
		}
		
		int daysLeft = this.dateCreated.getDayOfMonth() - LocalDate.now().minusDays(7L).getDayOfMonth();
		
		if (Integer.signum(daysLeft) == -1) {
			return null;
		}
		
		return valueOf(daysLeft);
	}

}
