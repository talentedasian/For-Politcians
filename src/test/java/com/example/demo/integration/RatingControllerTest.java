package com.example.demo.integration;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
	public void shouldReturn201IsCreated() throws Exception {
		when(service.saveRatings(any(AddRatingDTORequest.class), any())).thenReturn(politiciansRating);
		
		mvc.perform(post(create("/api/ratings/add-rating"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void assertEqualsDtoOutputs() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		
		mvc.perform(get(create("/api/ratings/ratingById?id=1")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("rating", 
				equalTo(politiciansRating.getRating())));
	}
	
	@Test
	public void assertEqualsDtoOutputsOnPoliticians() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		
		mvc.perform(get(create("/api/ratings/ratingById?id=1")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("politician.id", 
				equalTo(politiciansRating.getPolitician().getId().toString())))
			.andExpect(jsonPath("politician.name", 
				equalTo(politiciansRating.getPolitician().getName())))
			.andExpect(jsonPath("politician.rating", 
				equalTo(politiciansRating.getPolitician().getRating())))
			.andExpect(jsonPath("politician.satisfaction_rate", 
				equalTo(Rating.LOW.toString())));
	}
	
	@Test
	public void assertEqualsListOfDtoOutputs() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		
		when(service.findRatingsByFacebookEmail("test@gmail.com")).thenReturn(listOfPoliticiansRating);

		mvc.perform(get(create("/api/ratings/ratingByRater?email=test@gmail.com")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("[0].rating",
				equalTo(listOfPoliticiansRating.get(0).getRating())))
			.andExpect(jsonPath("[1].rating",
				equalTo(listOfPoliticiansRating.get(1).getRating())));
	}
	
	@Test
	public void assertEqualsUserRaterListOfDtoOutputs() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		
		when(service.findRatingsByFacebookEmail("test@gmail.com")).thenReturn(listOfPoliticiansRating);

		mvc.perform(get(create("/api/ratings/ratingByRater?email=test@gmail.com")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("[0].rater.email",
				equalTo(listOfPoliticiansRating.get(0).getRater().getEmail())))
			.andExpect(jsonPath("[0].rater.facebook_name",
				equalTo(listOfPoliticiansRating.get(0).getRater().getFacebookName())))
			.andExpect(jsonPath("[0].rater.political_party",
				equalTo(listOfPoliticiansRating.get(0).getRater().getPoliticalParties().toString())))
			.andExpect(jsonPath("[1].rater.email",
				equalTo(listOfPoliticiansRating.get(1).getRater().getEmail())))
			.andExpect(jsonPath("[1].rater.facebook_name",
				equalTo(listOfPoliticiansRating.get(1).getRater().getFacebookName())))
			.andExpect(jsonPath("[1].rater.political_party",
				equalTo(listOfPoliticiansRating.get(1).getRater().getPoliticalParties().toString())));
	}
	
	@Test
	public void assertEqualsUserRaterDtoOutputs() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		
		mvc.perform(get(create("/api/ratings/ratingById?id=1")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("rater.facebook_name",
				equalTo(politiciansRating.getRater().getFacebookName())))
			.andExpect(jsonPath("rater.political_party",
				equalTo(politiciansRating.getRater().getPoliticalParties().toString())))
			.andExpect(jsonPath("rater.email",
					equalTo(politiciansRating.getRater().getEmail())));
	}
	
}
