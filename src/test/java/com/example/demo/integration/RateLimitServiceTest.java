package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.model.entities.RateLimit;
import com.example.demo.repository.RateLimitRepository;
import com.example.demo.service.RateLimitingService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RateLimitServiceTest extends BaseClassTestsThatUsesDatabase {

	RateLimitingService service;
	@Autowired RateLimitRepository repo;
	
	final String ACCOUNT_NUMBER = "TGFLM-00000000000123";
	final String POLITICIAN_NUMBER = "123polNumber";
	
	RateLimit rateLimit = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
	
	@Test
	public void shouldDeleteBeforeSavingRateLimit() {
		service = new RateLimitingService(repo);
		
		repo.save(rateLimit);
		
		RateLimit rateLimitSaved = service.rateLimitUser(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		assertTrue(rateLimitSaved.getDateCreated().isAfter(rateLimit.getDateCreated()));
		assertEquals(1L, repo.countByIdAndPoliticianNumber(ACCOUNT_NUMBER, POLITICIAN_NUMBER));
	}
	
}
