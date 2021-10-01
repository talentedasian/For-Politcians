package com.example.demo.adapter.in.service;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class RateLimitingService {
	
	private final RateLimitRepository repo;

	public RateLimitingService(RateLimitRepository repo) {
		this.repo = repo;
	}

	@Transactional(readOnly = true)
	public Optional<RateLimit> findRateLimitInPolitician(String accountNumber, String politicianNumber) {
		PoliticianNumber.tryParse(politicianNumber);
		return repo.findUsingIdAndPoliticianNumber(accountNumber, new PoliticianNumber(politicianNumber));
	}

}
