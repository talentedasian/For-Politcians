package com.example.demo.unit.rateLimit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.RateLimit;

public class RateLimitTest {

	@Test
	public void shouldNotBeRateLimited() {
		var rate = RateLimit.withNotExpiredRateLimit("TGFLM-00000000000123", "123polNumber");
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldNotBeRateLimitedNullDate() {
		var rate = new RateLimit();
		rate.setId("1");
		rate.setPoliticianNumber("2");
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldNotBeRateLimitedAfter7Days() {
		var rate = new RateLimit();
		rate.setId("1");
		rate.setPoliticianNumber("2");
		rate.setDateCreated(LocalDate.now().minusDays(8L));
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldBeRateLimited() {
		var rate = new RateLimit();
		rate.setId("1");
		rate.setPoliticianNumber("2");
		rate.setDateCreated(LocalDate.now().minusDays(5L));
		
		assertFalse(rate.isNotRateLimited());
	}
	
}
