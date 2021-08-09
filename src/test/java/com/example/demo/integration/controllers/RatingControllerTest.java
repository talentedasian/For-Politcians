package com.example.demo.integration.controllers;

import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.exceptions.*;
import com.example.demo.hateoas.RatingAssembler;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.adapter.in.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import static com.example.demo.adapter.in.web.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static com.example.demo.domain.enums.Rating.mapToSatisfactionRate;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingsController.class)
public class RatingControllerTest {

	MockMvc mvc;

	@MockBean RatingService service;
	@MockBean PoliticiansService polService;
	@MockBean RatingDtoMapper mapper;
	@MockBean RateLimitingService rateLimitService;
	@MockBean RatingAssembler assembler;
	
	Politicians politician;
	PoliticiansRating politiciansRating;
	UserRater userRater;
	
	final String content = """
			{
				"id": "123",
				"rating": 1.00,
				"political_party": "dds"
			}
			""";

	final String SUBJECT = "test@gmail.com";
	final String ID = "1";
	final String NAME = "test";
	final String EMAIL = SUBJECT;
	final String FACEBOOK_NAME = NAME;
	
	@BeforeEach
	public void setup(WebApplicationContext wac) {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.alwaysDo(print())
				.build();
		
		politician = new PresidentialBuilder(new Politicians.PoliticiansBuilder("123polNumber")
				.setId(123)
				.setFirstName("Mirriam")
				.setLastName("Defensor")
				.setFullName()
				.setRating(new Rating(1D, 1D))
				.build())
			.build();
		
		userRater = new UserRater(FACEBOOK_NAME, PoliticalParty.DDS, EMAIL, "123accNumber", rateLimitService);
		
		politiciansRating = new PoliticiansRating(1, 1D, userRater, politician);
	}
	
	@Test
	public void shouldReturn401IsUnAuthorizedNullJwt() throws Exception {
		String message = "No jwt found on authorization header";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtNotFoundException(message));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", containsStringIgnoringCase("no jwt found on authorization header")))
			.andExpect(jsonPath("code", containsStringIgnoringCase("401")));
	}

	@Test
	public void shouldReturn401IsUnAuthorizedExpiredJwt() throws Exception {
		String message = "Jwt expired and not refreshable";

		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtExpiredException(message));

		mvc.perform(post(create("/api/ratings/rating"))
						.content(content)
						.header("Authorization", "Bearer " + createJwtWithFixedExpirationDate(SUBJECT, ID, NAME))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("err", containsStringIgnoringCase("jwt expired")))
				.andExpect(jsonPath("code", containsStringIgnoringCase("401")));
	}

	@Test
	public void shouldReturn401IsUnAuthorizedTamperedJwt() throws Exception {
		String message = "Jwt tampered. Either server has restarted w/o user's knowledge or it is indeed tampered.";

		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtTamperedExpcetion(message));

		mvc.perform(post(create("/api/ratings/rating"))
						.content(content)
						.header("Authorization", "Bearer " + createJwtWithFixedExpirationDate(SUBJECT, ID, NAME))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("err", containsStringIgnoringCase("jwt tampered")))
				.andExpect(jsonPath("code", containsStringIgnoringCase("401")));
	}

	@Test
	public void shouldReturn401IsUnAuthorizedWithBearerNotStart() throws Exception {
		String message = "Authorization Header must start with Bearer";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtMalformedFormatException(message));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
						.header("Authorization", "Not Bearer " + createJwtWithFixedExpirationDate(SUBJECT, ID, NAME))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", containsStringIgnoringCase("must start with bearer")))
			.andExpect(jsonPath("code", containsStringIgnoringCase("401")));
	}
	
	@Test
	public void shouldReturn201IsCreated() throws Exception {
		when(service.saveRatings(any(AddRatingDTORequest.class), any(HttpServletRequest.class))).thenReturn(politiciansRating);
		
		when(assembler.toModel(any())).thenCallRealMethod();
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("rating", equalTo(1.0D)))
			.andExpect(jsonPath("rater.email", equalTo(userRater.getEmail())))
			.andExpect(jsonPath("rater.name", equalTo(userRater.getFacebookName())))
			.andExpect(jsonPath("rater.political_party", equalTo(userRater.getPoliticalParties().toString())))
			.andExpect(jsonPath("rater.id", equalTo(userRater.getUserAccountNumber())))
			.andExpect(jsonPath("politician.name", equalTo(politician.getFullName())))
			.andExpect(jsonPath("politician.rating", equalTo(politician.getRating().getAverageRating())))
			.andExpect(jsonPath("politician.satisfaction_rate", equalTo(mapToSatisfactionRate(politician.getRating().getAverageRating()).toString())));
	}
	
	@Test
	public void shouldReturn404NotFoundbyId() throws Exception {
		when(service.findById("1")).thenThrow(new RatingsNotFoundException("No rating found by Id"));
		
		mvc.perform(get(create("/api/ratings/rating/1")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code", containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err", containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn404NotFoundByRater() throws Exception {
		when(service.findRatingsByFacebookEmail("dasdsa@gmail.com")).thenThrow(new RatingsNotFoundException("No rating found by Rater"));
		
		mvc.perform(get(create("/api/ratings/ratings?email=dasdsa@gmail.com")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code", containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err", containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn429WithDetailedExplanations() throws Exception { 
		when(service.saveRatings(any(AddRatingDTORequest.class), any(HttpServletRequest.class)))
		.thenThrow(new UserRateLimitedOnPoliticianException("User is rate limited on politician with 2 days left", 2L));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isTooManyRequests())
			.andExpect(header().string("Retry-After","2 days"))
			.andExpect(jsonPath("err", containsStringIgnoringCase("rate limited on politician ")))
			.andExpect(jsonPath("code", containsStringIgnoringCase("429")))
			.andExpect(jsonPath("optional", containsStringIgnoringCase("one request per week")));
	}
	
	@Test
	public void shouldReturn204NoContent() throws Exception {
		when(service.deleteById(1)).thenReturn(true);
		
		mvc.perform(delete(create("/api/ratings/rating/1")))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void shouldReturn404NotFoundWhenDeleting() throws Exception {
		when(service.deleteById(1)).thenReturn(false);
		
		mvc.perform(delete(create("/api/ratings/rating/1")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code", equalTo("404")))
			.andExpect(jsonPath("err", equalTo("Rating not found by 1")));
	}
	
	@Test
	public void shouldReturn204NoContentByAccNumber() throws Exception {
		when(service.deleteByAccountNumber("123accNumber")).thenReturn(true);
		
		mvc.perform(delete(create("/api/ratings/ratings/123accNumber")))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void shouldReturn404NotFoundWhenDeletingByAccNumber() throws Exception {
		when(service.deleteByAccountNumber("123accNumber")).thenReturn(false);
		
		mvc.perform(delete(create("/api/ratings/ratings/123accNumber")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code", equalTo("404")))
			.andExpect(jsonPath("err", equalTo("Rating not found by 123accNumber")));
	}
	
}
