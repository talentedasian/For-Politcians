package com.example.demo;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

public class RateLimitSpringHateoasTest extends BaseSpringHateoasTest {
	
	private String accountNumber = FacebookUserRaterNumberImplementor.with("test", "123").calculateEntityNumber().getAccountNumber();

	final RateLimit rateLimit = RateLimit.withNotExpiredRateLimit(accountNumber, "123polNumber"); 
	
	final String jwt = createJwtWithFixedExpirationDate("test@gmailcom", "123", "test"); 
	
	@Transactional
	@Test
	public void testHalFormsRateLimitStatus() throws Exception {
		limitingService.rateLimitUserForTests(rateLimit.getId(), rateLimit.getPoliticianNumber());
		
		this.mvc.perform(get(create("/rate-limit/123polNumber"))
				.header("Authorization", "Bearer " + jwt)
				.accept(HAL_FORMS_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(HAL_FORMS_JSON))
			.andExpect(jsonPath("days_left", equalTo("0")))
			.andExpect(jsonPath("account_number", equalTo(accountNumber)))
			.andExpect(jsonPath("politician_number", equalTo("123polNumber")))
			.andDo(document("rate-status", links(halLinks(),
					linkWithRel("self").description("Rate limit status of user"),
					linkWithRel("rating-account-number").description("All ratings by user's account number"))));
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		limitingService.deleteRateLimit(rateLimit.getId(), rateLimit.getPoliticianNumber());
	}
}
