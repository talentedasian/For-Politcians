package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.redis.RateLimiter;
import com.example.demo.repository.RateLimiterRepository;

@Service
public class RateLimiterService {

	private final RateLimiterRepository repo;
	private final RedisTemplate<byte[], byte[]> template;
	

	public RateLimiterService(RateLimiterRepository repo, RedisTemplate<byte[], byte[]> template) {
		super();
		this.repo = repo;
		this.template = template;
	}
	
	public Optional<RateLimiter> findRateLimitByUserAccountNumber(String accountNumber) {
		Optional<RateLimiter> rateLimit = repo.findByAccountNumber(accountNumber);
		
		return rateLimit;
	}
	
	public Long findExpirationOfRateLimit(String key) {
		return template.getConnectionFactory().getConnection().ttl(("rate_limit:" + key).getBytes()); 
	}
	
	
	
}
