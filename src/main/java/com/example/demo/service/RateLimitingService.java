package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entities.RateLimit;
import com.example.demo.repository.RateLimitRepository;

@Service
public class RateLimitingService {
	
	private final RateLimitRepository repo;

	public RateLimitingService(RateLimitRepository repo) {
		super();
		this.repo = repo;
	}
	
	@Transactional(readOnly = true)
	public RateLimit findById(String id) {
		return repo.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public RateLimit findRateLimitInPolitician(String accountNumber, String politicianNumber) {
		return repo.findByIdAndPoliticianNumber(accountNumber, politicianNumber).orElse(null);
	}
	
	public boolean isNotRateLimited(String accNumber, String polNumber) {
		 return this.findRateLimitInPolitician(accNumber, polNumber).isNotRateLimited();
	}
	
	public Integer daysLeftOfBeingRateLimited(String accNumber, String polNumber) {
		 return this.findRateLimitInPolitician(accNumber, polNumber).daysLeftOfBeingRateLimited();
	}

}
