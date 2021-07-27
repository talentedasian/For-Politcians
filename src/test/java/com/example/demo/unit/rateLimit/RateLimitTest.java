package com.example.demo.unit.rateLimit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.RateLimit;

public class RateLimitTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	final String ACCOUNT_NUMBER = "TGFLM-00000000000123";
	
	@Test
	public void shouldNotBeRateLimited() {
		var rate = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldBeNullDaysLeftWhenNotRateLimited() {
		var rate = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		assertNull(rate.daysLeftOfBeingRateLimited());
	}
	
	@Test
	public void shouldNotBeRateLimitedWhenNullDate() {
		var rate = new RateLimit();
		rate.setId("1");
		rate.setPoliticianNumber("2");
		
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
