package com.example.demo.integration;

import static java.net.URI.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;
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
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.model.enums.Rating;
import com.example.demo.service.RatingService;
import com.jayway.jsonpath.JsonPath;

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
		
		politiciansRating = new PoliticiansRating(1, 0.01D, new UserRater("test", PoliticalParty.DDS, "test@gmail.com"), politician);
				
		politicianDTO = new PoliticianDTO("test", "1", 0.01D, Rating.LOW);
		
		ratingDTO = new RatingDTO(0.00D, new UserRater("test", PoliticalParty.DDS, "test@gmail.com"), politicianDTO);
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
				equalTo(politiciansRating.getRating())))
			.andExpect(jsonPath("politician.satisfaction_rate", 
				equalTo(Rating.LOW.toString())));
	}
	
	@Test
	public void assertEqualsUserRaterDtoOutputs() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		
		mvc.perform(get(create("/api/ratings/ratingById?id=1")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("rater.facebook_name",
				equalTo(politiciansRating.getRater().getFacebookName())))
			.andExpect(jsonPath("rater.political_party",
				equalTo(politiciansRating.getRater().getPoliticalParties().toString())));
	}
	
}
