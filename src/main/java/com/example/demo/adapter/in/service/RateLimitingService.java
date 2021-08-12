package com.example.demo.adapter.in.service;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
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
		return repo.findUsingIdAndPoliticianNumber(accountNumber, politicianNumber);
	}
	
	/*
	 * Rate limit a user in a particular politician. Also delete
	 * the first instance of the rate limit so that the most updated
	 * rate limit is reflected and not the outdated one. 
	 */
	@Transactional
	public RateLimit rateLimitUser(String accNumber, String polNumber) {
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
	public RateLimit rateLimitUserForTests(String accNumber, String polNumber) {
		var rateLimitToBeSaved = RateLimit.withNotExpiredRateLimit(accNumber, polNumber);
		
		deleteRateLimit(accNumber, polNumber);
		RateLimit rateLimit = repo.save(rateLimitToBeSaved);
		return rateLimit;
	}
	
	/*
	 * Probably going to change the access modifier to avoid 
	 * being used everywhere. This should only be used for tests.
	 */
	public void deleteRateLimit(String id, String politicianNumber) {
		repo.deleteUsingIdAndPoliticianNumber(id, politicianNumber);
	}
	
	public boolean isNotRateLimited(String accNumber, String polNumber) {
		Optional<RateLimit> rateLimit = this.findRateLimitInPolitician(accNumber, polNumber);
		if (rateLimit.isEmpty()) {
			return true;
		}
		
		return rateLimit.get().isNotRateLimited();
	}
	
	public Integer daysLeftOfBeingRateLimited(String accNumber, String polNumber) {
		Optional<RateLimit> rateLimit = this.findRateLimitInPolitician(accNumber, polNumber);
		 if (rateLimit.isPresent()) {
			 Integer daysLeft = rateLimit.get().daysLeftOfBeingRateLimited();
			 if (rateLimit.get().daysLeftOfBeingRateLimited() == null) {
				 return 0;
			 }
			 return daysLeft; 
		 } 
		 
		 return 0;
	}

}
