package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.config.RedisExpirationUtils;
import com.example.demo.model.redis.RateLimiter;
import com.example.demo.repository.RateLimiterRepository;

@Service
public class RateLimiterService {

	private final RateLimiterRepository repo;
	private final RedisExpirationUtils redisUtils;

	public RateLimiterService(RateLimiterRepository repo, RedisExpirationUtils redisUtils) {
		super();
		this.repo = repo;
		this.redisUtils = redisUtils;
	}
	
	public Optional<RateLimiter> findRateLimitByUserAccountNumber(String accountNumber) {
		Optional<RateLimiter> rateLimit = repo.findByAccountNumber(accountNumber);
		
		return rateLimit;
	}
	
	public RateLimiter save(RateLimiter rateLimiter) {
		return repo.save(rateLimiter);
	}
	
	public void delete(RateLimiter rateLimiter) {
		repo.delete(rateLimiter);
	}
	
	public Long getExpirationTimeOfKey(String key) {
		return redisUtils.getTimeToLiveOfKey(key);
	}
	
}
