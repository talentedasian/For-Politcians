package com.example.demo.unit.rateLimit;

import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
		rate.setId(ACCOUNT_NUMBER);
		rate.setPoliticianNumber(POLITICIAN_NUMBER);
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldBeRateLimited() {
		var rate = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		assertFalse(rate.isNotRateLimited());
	}
	
}
