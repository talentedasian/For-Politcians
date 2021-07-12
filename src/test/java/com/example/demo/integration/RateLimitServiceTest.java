package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import com.example.demo.model.entities.RateLimit;
import com.example.demo.repository.RateLimitRepository;
import com.example.demo.service.RateLimitingService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@EnableAutoConfiguration
public class RateLimitServiceTest {

	RateLimitingService service;
	@Autowired RateLimitRepository repo;
	
	RateLimit rateLimit = RateLimit.withNotExpiredRateLimit("TGFLM-00000000000123", "123polNumber");
	
	@Test
	public void shouldDeleteBeforeSavingRateLimit() {
		service = new RateLimitingService(repo);
		
		repo.save(rateLimit);
		
		RateLimit rateLimitSaved = service.rateLimitUser("123accNumber", "123polNumber");
		
		assertTrue(rateLimit.getDateCreated().isBefore(rateLimitSaved.getDateCreated()));
		assertEquals(1L, repo.countByIdAndPoliticianNumber("123accNumber", "123polNumber"));
		
	}
	
}
