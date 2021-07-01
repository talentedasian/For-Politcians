package com.example.demo.service;

import java.util.Optional;

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
	public Optional<RateLimit> findRateLimitInPolitician(String accountNumber, String politicianNumber) {
		return repo.findByIdAndPoliticianNumber(accountNumber, politicianNumber);
	}
	
	//rate limit a user in a particular politician
	@Transactional
	public RateLimit rateLimitUser(String accountNumber, String politicianNumber) {
		var rateLimitToBeSaved = new RateLimit(accountNumber, politicianNumber);
		
		deleteRateLimit(accountNumber, politicianNumber);
		RateLimit rateLimit = repo.save(rateLimitToBeSaved);
		return rateLimit;
	}
	
	private void deleteRateLimit(String id, String politicianNumber) {
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
