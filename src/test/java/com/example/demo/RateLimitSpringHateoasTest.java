package com.example.demo;

import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate;
import static java.net.URI.create;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class RateLimitSpringHateoasTest {

	@Autowired MockMvc mvc;

	final String NAME = "test";
	final String ID = "123";
	final String ACCOUNT_NUMBER = FacebookUserRaterNumberImplementor.with(NAME, ID).calculateEntityNumber().getAccountNumber();
	final String POLITICIAN_NUMBER = "123polNumber";

	RateLimit rateLimit;
	
	final String jwt = createJwtWithFixedExpirationDate("test@gmailcom", ACCOUNT_NUMBER, NAME);

	@Autowired RateLimitRepository rateLimitRepo;

	RateLimitingService limitingService;

	@BeforeEach
	public void setup() {
		limitingService = new RateLimitingService(rateLimitRepo);

		rateLimit = RateLimit.withNotExpiredRateLimit(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
	}

	@Transactional
	@Test
	public void testHalFormsRateLimitStatusResponse() throws Exception {
		limitingService.rateLimitUserForTests(ACCOUNT_NUMBER, POLITICIAN_NUMBER);
		
		this.mvc.perform(get(create("/rate-limit/" + POLITICIAN_NUMBER))
				.header("Authorization", "Bearer " + jwt))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON));
//			.andDo(document("rate-status", links(halLinks(),
//					linkWithRel("self").description("Rate limit status of user"),
//					linkWithRel("rating-account-number").description("All ratings by user's account number"))));
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		limitingService.deleteRateLimit(rateLimit.id(), rateLimit.politicianNumber());
	}

}
