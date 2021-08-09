package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.adapter.out.repository.RateLimitRepository;
import com.example.demo.adapter.in.service.RateLimitingService;

import testAnnotations.DatabaseTest;

@DatabaseTest
public class RateLimitServiceTest extends BaseClassTestsThatUsesDatabase {

	RateLimitingService service;
	@Autowired RateLimitRepository repo;
	
	final String ACCOUNT_NUMBER = "TGFLM-00000000000123";
	final String POLITICIAN_NUMBER = "123polNumber";
	
	RateLimit rateLimit = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
	
	@BeforeEach
	public void setup() {
		service = new RateLimitingService(repo);
	}
	
	@Test
	public void testCustomCountQueryByAccountNumberAndPoliticianNumber() {
		repo.save(rateLimit);
		
		assertEquals(1L, repo.countByIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER));
	}
	
	@Test
	public void shouldDeleteFirstBeforeSavingRateLimit() {
		repo.save(rateLimit);
		
		var deletedRateLimit = repo.findByIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		RateLimit rateLimitSaved = service.rateLimitUser(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		assertTrue(isPreviousRateLimitOverwritten(deletedRateLimit.get().getDateCreated(), rateLimitSaved.getDateCreated()));
		assertEquals(1L, repo.countByIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER));
	}
	
	private boolean isPreviousRateLimitOverwritten(LocalDate previous, LocalDate latest) {
		return previous.isBefore(latest);
	}
	
}
