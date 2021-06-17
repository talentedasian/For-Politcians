package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.redis.RateLimiter;
import com.example.demo.repository.RateLimiterRepository;

@Service
public class RateLimiterService {

	@Autowired
	private final RateLimiterRepository repo;

	public RateLimiterService(RateLimiterRepository repo) {
		super();
		this.repo = repo;
	}
	
	public Optional<RateLimiter> findRateLimitByUserAccountNumber(String accountNumber) {
		Optional<RateLimiter> rateLimit = repo.findByAccountNumber(accountNumber);
		
		return rateLimit;
	}
	
}
