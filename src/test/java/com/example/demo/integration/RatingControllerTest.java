package com.example.demo.integration;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.RateLimitedException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.service.RatingService;

@WebMvcTest(RatingsController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false, print = MockMvcPrint.DEFAULT)
public class RatingControllerTest {
	
	@Autowired
	public MockMvc mvc;

	@MockBean
	public RatingService service;
	@MockBean
	public RatingDtoMapper mapper;
	
	public Politicians politician;
	public PoliticiansRating politiciansRating;
	public RatingDTO ratingDTO;
	public PoliticianDTO politicianDTO;
	public UserRater userRater;
	
	private final String content = """
			{
				"id": "123polNumber",
				"rating": 1.00,
				"political_party": "dds"
			}
			""";
	
	@Mock
	public HttpServletRequest req;
	
	@BeforeEach
	public void setup() {
		politician = new Politicians();
		politician.setId(1);
		politician.setFirstName("Mirriam");
		politician.setLastName("Defensor");
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
				containsStringIgnoringCase("401")));
	}
	
	@Test
	public void shouldReturn401IsUnAuthorizedWithBearerNotStart() throws Exception {
		String message = "Authorization Header must start with Bearer";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtMalformedFormatException(message));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("must start with bearer")))
			.andExpect(jsonPath("code", 
				containsStringIgnoringCase("401")));
	}
	
	@Test
	public void shouldReturn404NotFoundbyId() throws Exception {
		when(service.findById("1")).thenThrow(new RatingsNotFoundException("No rating found by Id"));
		
		mvc.perform(get(create("/api/ratings/rating/1")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code",
					containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err",
				containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn404NotFoundByRater() throws Exception {
		when(service.findRatingsByFacebookEmail("dasdsa@gmail.com")).thenThrow(new RatingsNotFoundException("No rating found by Rater"));
		
		mvc.perform(get(create("/api/ratings/rating?email=dasdsa@gmail.com")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code",
					containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err",
				containsStringIgnoringCase("no rating found")));
	}
	
	@Test
	public void shouldReturn409WithDetailedExplanations() throws Exception { 
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new RateLimitedException("User has been rate limited for 172800 seconds", 172800L));
		
		mvc.perform(post(create("/api/ratings/rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("rate limited for 172800 seconds")))
			.andExpect(jsonPath("code", 
				containsStringIgnoringCase("429")))
			.andExpect(jsonPath("optional", 
				containsStringIgnoringCase("one request per week")))
				.andExpect(header().string("Retry-After","172800"));
	}
	
}
