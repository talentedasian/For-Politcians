package com.example.demo.integration.redis;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;

import com.example.demo.model.redis.RateLimiter;
import com.example.demo.repository.RateLimiterRepository;

@DataRedisTest
public class RedisTest {
	
	@Autowired
	public RedisOperations<Object, Object> ops;
	@Autowired
	public RateLimiterRepository repo;
	
	@Test
	public void shouldEqualAccountNumberStoredInRedis() {
		RateLimiter rateLimiter = new RateLimiter();
		rateLimiter.setPoliticianNumber("2222");
		rateLimiter.setAccountNumber("8000");
		rateLimiter.setExpiration(604800L);
		
		RateLimiter savedRateLimiter = repo.save(rateLimiter);
		
		assertThat(repo.findByAccountNumber("8000").get().getAccountNumber(), 
				equalTo(savedRateLimiter.getAccountNumber()));
	}
	
	@Test
	public void testExpirationTimeOfRedisKey() throws InterruptedException {
		RateLimiter queriedRateLimiter = repo.findByAccountNumber("8000").get();
		
		assertThat((Long)ops.execute((RedisConnection connection) -> connection.execute("TTL",
                ("rate_limit:" + queriedRateLimiter.getId()).getBytes())),
				 is(greaterThan(604000L)));
		
		repo.delete(queriedRateLimiter);
	}

}
