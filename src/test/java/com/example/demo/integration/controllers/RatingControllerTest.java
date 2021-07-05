package com.example.demo.integration.controllers;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static com.example.demo.mockMvcUtils.MockMvcPathUtils.createPath;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RateLimitingService;
import com.example.demo.service.RatingService;

@WebMvcTest(RatingsController.class)
public class RatingControllerTest {
	
	@RegisterExtension final RestDocumentationExtension restDocumentation = new RestDocumentationExtension ("custom");
	
	MockMvc mvc;

	@MockBean RatingService service;
	@MockBean PoliticiansService polService;
	@MockBean RatingDtoMapper mapper;
	@MockBean RateLimitingService rateLimitService;
	
	Map<String, Object> uriVars = new HashMap<>();
	
	Politicians politician;
	PoliticiansRating politiciansRating;
	RatingDTO ratingDTO;
	PoliticianDTO politicianDTO;
	UserRater userRater;
	
	Class<?>[] postClasses = { AddRatingDTORequest.class, HttpServletRequest.class };
	Class<RatingsController> ratingClass= RatingsController.class;
	
	final String content = """
			{
				"id": "123polNumber",
				"rating": 1.00,
				"political_party": "dds"
			}
			""";
	
	@Mock HttpServletRequest req;
	
	@BeforeEach
	public void setup(WebApplicationContext wac, RestDocumentationContextProvider provider) {
		this.mvc = webAppContextSetup(wac)
				.apply(documentationConfiguration(provider))
				.alwaysDo(print())
				.build();
		
		politician = new Politicians();
		politician.setId(1);
		politician.setFirstName("Mirriam");
		politician.setLastName("Defensor");
		politician.setRating(new Rating(1D, 1D, mock(LowSatisfactionAverageCalculator.class)));
		
		userRater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accNumber");
		
		politiciansRating = new PoliticiansRating(1, 1D, userRater, politician);
		
		uriVars.put("id", 1);
	}
	
	@Test
	public void shouldReturn401IsUnAuthorized() throws Exception {
		String message = "No jwt found on authorization header";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtNotFoundException(message));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("no jwt found on authorization header")))
			.andExpect(jsonPath("code", 
					containsStringIgnoringCase("401")))
			.andDo(document("rate-no-jwt"));
	}
	
	@Test
	public void shouldReturn401IsUnAuthorizedWithBearerNotStart() throws Exception {
		String message = "Authorization Header must start with Bearer";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtMalformedFormatException(message));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.header("Authorization", "Not Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "1", "test"))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("must start with bearer")))
			.andExpect(jsonPath("code", 
				containsStringIgnoringCase("401")))
			.andDo(document("rate-start-bearer-jwt"));
	}
	
	@Test
	public void shouldReturn201IsCreated() throws Exception {
		when(service.saveRatings(any(AddRatingDTORequest.class), any(HttpServletRequest.class))).thenReturn(politiciansRating);
		
		
		mvc.perform(post(createPath("saveRating", ratingClass, POST, null, postClasses))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("rating", 
					equalTo(1.0D)))
			.andExpect(jsonPath("rater.email", 
					equalTo(userRater.getEmail())))
			.andExpect(jsonPath("rater.facebook_name", 
					equalTo(userRater.getFacebookName())))
			.andExpect(jsonPath("rater.political_party", 
					equalTo(userRater.getPoliticalParties().toString())));
	}
	
	@Test
	public void shouldReturn404NotFoundbyId() throws Exception {
		when(service.findById("1")).thenThrow(new RatingsNotFoundException("No rating found by Id"));
		
		mvc.perform(get(createPath("getRatingById", ratingClass, GET, uriVars, String.class)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code",
					containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err",
					containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn404NotFoundByRater() throws Exception {
		when(service.findRatingsByFacebookEmail("dasdsa@gmail.com")).thenThrow(new RatingsNotFoundException("No rating found by Rater"));
		System.out.println(createPath("getRatingByRater", ratingClass, GET, null, String.class).concat("?email=dasdsa@gmail.com") + " tite");
		mvc.perform(get(URI.create(createPath("getRatingByRater", ratingClass, GET, null, String.class).concat("?email=dasdsa@gmail.com"))))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code",
					containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err",
					containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn429WithDetailedExplanations() throws Exception { 
		when(service.saveRatings(any(AddRatingDTORequest.class), any(HttpServletRequest.class)))
		.thenThrow(new UserRateLimitedOnPoliticianException("User is rate limited on politician with 2 days left", 2L));
		
		mvc.perform(post(URI.create(createPath("saveRating", ratingClass, POST, null, AddRatingDTORequest.class, HttpServletRequest.class)))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("rate limited on politician ")))
			.andExpect(jsonPath("code", 
				containsStringIgnoringCase("429")))
			.andExpect(jsonPath("optional", 
				containsStringIgnoringCase("one request per week")))
			.andExpect(header().string("Retry-After","2 days"))
			.andDo(document("rate-limited"));
	}
	
}
