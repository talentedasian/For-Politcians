package com.example.demo.adapter.in.service;

import com.example.demo.domain.entities.RateLimit;
import com.example.demo.adapter.out.repository.RateLimitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RateLimitingService {
	
	private final RateLimitRepository repo;

	public RateLimitingService(RateLimitRepository repo) {
		this.repo = repo;
	}
	
	@Transactional(readOnly = true)
	public Optional<RateLimit> findById(String id) {
		return repo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<RateLimit> findRateLimitInPolitician(String accountNumber, String politicianNumber) {
		return repo.findByIdAndPoliticianNumber(accountNumber, politicianNumber);
	}
	
	/*
	 * Rate limit a user in a particular politician. Also delete
	 * the first instance of the rate limit so that the most updated
	 * rate limit is reflected and not the outdated one. 
	 */
	@Transactional
	public RateLimit rateLimitUser(String accountNumber, String politicianNumber) {
		var rateLimitToBeSaved = new RateLimit(accountNumber, politicianNumber);
		
		deleteRateLimit(accountNumber, politicianNumber);
		RateLimit rateLimit = repo.save(rateLimitToBeSaved);
		return rateLimit;
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
		repo.deleteByIdAndPoliticianNumber(id, politicianNumber);
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
