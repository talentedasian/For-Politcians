package com.example.demo.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.model.enums.Rating;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class RatingsControllerTest {
	
	@Mock
	public RatingService service;
	
	public RatingsController controller;
	
	public Politicians politician;
	public PoliticiansRating politiciansRating;
	public RatingDTO ratingDTO;
	public PoliticianDTO politicianDTO;
	
	
	@BeforeEach
	public void setup() {
		controller = new RatingsController(service);
		
		politician = new Politicians();
		politician.setId(1);
		politician.setName("Mirriam Defensor");
		politician.setRating(0.00D);
		politician.setTotalRating(0.00D);
		politiciansRating = new PoliticiansRating(1, 0.00D, new UserRater("test", PoliticalParty.DDS), politician);
		politicianDTO = new PoliticianDTO("Mirriam Defensor", "1", 0.00D, Rating.LOW);
		ratingDTO = new RatingDTO(0.00D, new UserRater("test", PoliticalParty.DDS), politicianDTO);
	}
	
	@Test
	public void	assertEqualsReturnedDto() {
		when(service.findById("1")).thenReturn(politiciansRating);
		
		ResponseEntity<RatingDTO> response = controller.getRatingById("1");
		
		assertThat(response.getBody().getRater().getFacebookName(), 
				equalTo(ratingDTO.getRater().getFacebookName()));
		assertThat(response.getBody().getRater().getPoliticalParties(), 
				equalTo(ratingDTO.getRater().getPoliticalParties()));
		assertThat(response.getBody().getRating(), 
				equalTo(ratingDTO.getRating()));
	}

}
