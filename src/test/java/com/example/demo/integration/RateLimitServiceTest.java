package com.example.demo.integration;

import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import java.time.LocalDate;

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
	
	private boolean isPreviousRateLimitOverwritten(LocalDate previous, LocalDate latest) {
		return previous.isBefore(latest);
	}
	
}
