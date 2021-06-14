package com.example.demo.integration.redis;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import com.example.demo.model.redis.RateLimiter;
import com.example.demo.repository.RateLimiterRepository;

@DataRedisTest	
public class RedisTest {
	
	@Autowired
	public RateLimiterRepository repo;
	
	@Test
	public void shouldEqualAccountNumberStoredInRedis() {
		RateLimiter rateLimiter = new RateLimiter();
		rateLimiter.setPoliticianNumber("0000");
		rateLimiter.setAccountNumber("1111");
		
		RateLimiter savedRateLimiter = repo.save(rateLimiter);
		
		assertThat(repo.findByAccountNumber("1111").get().getAccountNumber(), 
				equalTo(savedRateLimiter.getAccountNumber()));
	}

}
