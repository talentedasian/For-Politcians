package com.example.demo.adapter.in.service;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.PoliticianNumber;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
	
	/*
	 * Rate limit a user in a particular politician. Also delete
	 * the first instance of the rate limit so that the most updated
	 * rate limit is reflected and not the outdated one. 
	 */
	@Transactional
	public RateLimit rateLimitUser(String accNumber, PoliticianNumber polNumber) {
		var rateLimit = new RateLimit(accNumber, polNumber, LocalDate.now());

		deleteRateLimit(accNumber, polNumber);
		RateLimit rateLimitSaved = repo.save(rateLimit);
		return rateLimitSaved;
	}

	/*
	 * Probably going to change the access modifier to avoid 
	 * being used everywhere. This should only be used for tests.
	 */
	@Transactional
	public void deleteRateLimit(String id, PoliticianNumber politicianNumber) {
		repo.deleteUsingIdAndPoliticianNumber(id, politicianNumber);
	}

	@Transactional(readOnly = true)
	public boolean isNotRateLimited(String accNumber, String polNumber) {
		Optional<RateLimit> rateLimit = this.findRateLimitInPolitician(accNumber, polNumber);
		if (rateLimit.isEmpty()) {
			return true;
		}
		
		return rateLimit.get().isNotRateLimited();
	}

}
