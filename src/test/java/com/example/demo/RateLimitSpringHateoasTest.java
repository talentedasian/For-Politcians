package com.example.demo;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static java.net.URI.create;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

public class RateLimitSpringHateoasTest extends BaseSpringHateoasTest {
	
	private String accountNumber = FacebookUserRaterNumberImplementor.with("test", "123").calculateEntityNumber().getAccountNumber();

	final RateLimit rateLimit = new RateLimit(accountNumber, "123polNumber"); 
	
	final String jwt = createJwtWithFixedExpirationDate("test@gmailcom", "123ar", "test"); 
	
	@Transactional
	@Test
	public void testHalFormsRateLimitStatus() throws Exception {
		limitingService.rateLimitUser(rateLimit.getId(), rateLimit.getPoliticianNumber());
		System.out.println(limitingService.daysLeftOfBeingRateLimited(accountNumber, "123polNumber"));
		
		this.mvc.perform(get(create("/rate-limit/123polNumber"))
				.header("Authorization", "Bearer " + jwt)
				.accept(HAL_FORMS_JSON))
			.andExpect(status().isOk());
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		limitingService.deleteRateLimit(rateLimit.getId(), rateLimit.getPoliticianNumber());
	}
}
