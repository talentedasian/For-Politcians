package com.example.demo.integration.redis;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.config.RedisExpirationUtils;
import com.example.demo.model.redis.RateLimiter;
import com.example.demo.service.RateLimiterService;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class RedisExpirationUtilsTest {

	@Autowired
	public RedisExpirationUtils utils;
	@Autowired
	public RateLimiterService service;
	
	@Test
	public void dummy() {
		var rateLimiterToBeSaved = new RateLimiter();
		rateLimiterToBeSaved.setAccountNumber("4444");
		rateLimiterToBeSaved.setPoliticianNumber("3333");
		rateLimiterToBeSaved.setExpiration(80L);
		
		service.save(rateLimiterToBeSaved);
		
		RateLimiter rateLimiter = service.findRateLimitByUserAccountNumber("4444").get();
		
		assertThat(utils.getTimeToLiveOfKey(rateLimiter.getId()), 
				is(Matchers.lessThanOrEqualTo(80L)));	
		
		service.delete(rateLimiter);
	}

}
