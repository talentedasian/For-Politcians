package com.example.demo;

import static com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate;
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

import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

public class RateLimitSpringHateoasTest extends BaseSpringHateoasTest {
	
	final String NAME = "test";
	final String ID = "123";
	final String ACCOUNT_NUMBER = FacebookUserRaterNumberImplementor.with(NAME, ID).calculateEntityNumber().getAccountNumber();
	final String POLITICIAN_NUMBER = "123polNumber";

	final RateLimit rateLimit = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER); 
	
	final String jwt = createJwtWithFixedExpirationDate("test@gmailcom", ID, NAME); 
	
	@Transactional
	@Test
	public void testHalFormsRateLimitStatusResponse() throws Exception {
		limitingService.rateLimitUserForTests(rateLimit.getId(), rateLimit.getPoliticianNumber());
		
		this.mvc.perform(get(create("/rate-limit/" + POLITICIAN_NUMBER))
				.header("Authorization", "Bearer " + jwt)
				.accept(HAL_FORMS_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(HAL_FORMS_JSON))
			.andExpect(jsonPath("days_left", equalTo("0")))
			.andExpect(jsonPath("account_number", equalTo(ACCOUNT_NUMBER)))
			.andExpect(jsonPath("politician_number", equalTo(POLITICIAN_NUMBER)))
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
