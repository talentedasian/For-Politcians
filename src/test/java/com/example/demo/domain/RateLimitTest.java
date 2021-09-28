package com.example.demo.domain;

import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.PoliticianNumber;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Domain")
public class RateLimitTest {

	final PoliticianNumber POLITICIAN_NUMBER = NumberTestFactory.POL_NUMBER();
	final String ACCOUNT_NUMBER = NumberTestFactory.ACC_NUMBER().accountNumber();
	
	@Test
	public void shouldNotBeRateLimited() {
		var rate = new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now().minusDays(8));
		
		assertTrue(rate.isNotRateLimited());
	}

	@Test
	public void shouldThrowIllegalStateExceptionWhenRetrievingDaysLeftToRateWhenRateIsNotRateLimited() {
		var rate = new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now().minusDays(9));

		assertThrows(IllegalStateException.class, () -> rate.daysLeftOfBeingRateLimited(), "should be rate limited");
	}

	@Test
	public void shouldReturnCorrectDaysLeftToRateAgainWhenRateIsRateLimited() {
		var rate = new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now());

		assertThat(rate.daysLeftOfBeingRateLimited())
				.isEqualTo(7);
	}
	
	@Test
	public void shouldNotBeRateLimitedWhenNullDate() {
		var rate = new RateLimit();
		rate.setId(ACCOUNT_NUMBER);
		rate.setPoliticianNumber(POLITICIAN_NUMBER.politicianNumber());
		
		assertTrue(rate.isNotRateLimited());
	}
	
	@Test
	public void shouldBeRateLimited() {
		var rate = new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now());
		
		assertFalse(rate.isNotRateLimited());
	}
	
}
