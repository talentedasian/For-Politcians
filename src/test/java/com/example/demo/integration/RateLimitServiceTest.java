package com.example.demo.integration;

import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.politicians.PoliticianNumber;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import java.time.LocalDate;

@DatabaseTest
public class RateLimitServiceTest extends BaseClassTestsThatUsesDatabase {

	RateLimitingService service;
	@Autowired RateLimitRepository repo;
	
	final String ACCOUNT_NUMBER = "TGFLM-00000000000123";
	final PoliticianNumber POLITICIAN_NUMBER = NumberTestFactory.POL_NUMBER();

	RateLimit rate = new RateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER, LocalDate.now().minusDays(8));
	
	@BeforeEach
	public void setup() {
		service = new RateLimitingService(repo);
	}
	
}
