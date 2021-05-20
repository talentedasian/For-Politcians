package com.example.demo.integration;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;import com.example.demo.controller.RatingsController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.JwtMalformedFormatException;
import com.example.demo.exceptions.JwtNotFoundException;
import com.example.demo.exceptions.RatingsNotFoundException;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.model.enums.Rating;
import com.example.demo.service.RatingService;

@WebMvcTest(RatingsController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false, print = MockMvcPrint.DEFAULT)
public class RatingControllerTest {
	
	@Autowired
	public MockMvc mvc;

	@MockBean
	public RatingService service;
	
	public Politicians politician;
	public PoliticiansRating politiciansRating;
	public RatingDTO ratingDTO;
	public PoliticianDTO politicianDTO;
	public UserRater userRater;
	
	private final String content = """
			{
				"politicianName": "test",
				"rating": 1.00,
				"politicalParty": "dds"
			}
			""";
	
	@Mock
	public HttpServletRequest req;
	
	@BeforeEach
	public void setup() {
		politician = new Politicians();
		politician.setId(1);
		politician.setName("Mirriam Defensor");
		politician.setRating(0.01D);
		politician.setTotalRating(0.01D);
		
		userRater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com");
		
		politiciansRating = new PoliticiansRating(1, 0.01D, userRater, politician);
				
		politicianDTO = new PoliticianDTO("test", "1", 0.01D, Rating.LOW);
		
		ratingDTO = new RatingDTO(0.00D, userRater, politicianDTO);
	}
	
	@Test
	public void shouldReturn401IsUnAuthorized() throws Exception {
		String message = "No jwt found on authorization header";
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenThrow(new JwtNotFoundException(message));
		
		mvc.perform(post(create("/api/ratings/add-rating"))
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
		
		mvc.perform(post(create("/api/ratings/add-rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("must start with bearer")))
			.andExpect(jsonPath("code", 
				containsStringIgnoringCase("401")));
	}
	
	@Test
	public void shouldReturn404NotFound() throws Exception {
		when(service.findById("1")).thenThrow(new RatingsNotFoundException("No rating found by Id"));
		
		mvc.perform(get(create("/api/ratings/ratingById?id=1")))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("code",
					containsStringIgnoringCase("404")))
			.andExpect(jsonPath("err",
				containsStringIgnoringCase("no rating found")));
	}
	
}
