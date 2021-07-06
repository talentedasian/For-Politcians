package com.example.demo.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.RatingsController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.hateoas.RatingAssembler;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.model.enums.Rating;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class RatingsControllerTest {
	
	@Mock RatingService service;
	
	@Mock RatingAssembler assembler;
	
	RatingsController controller;
	
	Politicians politician;
	PoliticiansRating politiciansRating;
	RatingDTO ratingDTO;
	PoliticianDTO politicianDTO;	
	final UserRater userRater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accountNumber");
	
	@BeforeEach
	public void setup() {
		controller = new RatingsController(service, assembler);
		
		politician = new Politicians();
		politician.setPoliticianNumber("123polNumber");
		politician.setFirstName("Mirriam");
		politician.setLastName("Defensor");
		politician.setFullName("Mirriam Defensor");
		politician.setRating(new com.example.demo.model.entities.Rating(9.67D, 9.67D, new LowSatisfactionAverageCalculator(9.07D, 0D)));
		
		politiciansRating = new PoliticiansRating(1, 0.00D, userRater, politician);
		
		politicianDTO = new PoliticianDTO("Mirriam Defensor", "1", 0.00D, Rating.LOW);
		
		ratingDTO = new RatingDtoMapper().mapToDTO(politiciansRating);
	}
	
	@Test
	public void assertEqualsDtoOutputs() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		when(assembler.toModel(ratingDTO)).thenReturn(EntityModel.of(ratingDTO));
		
		ResponseEntity<EntityModel<RatingDTO>> response = controller.getRatingById("1");
		
		RatingDTO politicianResponse = response.getBody().getContent();
		
		assertThat(politicianResponse.getRating(),
				equalTo(politiciansRating.getRating()));
	}
	
	@Test
	public void assertEqualsDtoOutputsOnPoliticians() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		when(assembler.toModel(ratingDTO)).thenReturn(EntityModel.of(ratingDTO));
		
		ResponseEntity<EntityModel<RatingDTO>> response = controller.getRatingById("1");
		
		RatingDTO politiciaResponse = response.getBody().getContent();
		
		assertThat(politiciaResponse.getPolitician().getId().toString(),
				equalTo(politiciansRating.getPolitician().getPoliticianNumber()));
		assertThat(politiciaResponse.getPolitician().getName(),
				equalTo(politiciansRating.getPolitician().getFirstName() + " " + politiciansRating.getPolitician().getLastName()));
	}
	
	@Test
	public void assertEqualsPoliticiansListOfDtoOutputs() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		
		when(service.findRatingsByFacebookEmail("test@gmail.com")).thenReturn(listOfPoliticiansRating);
		
		ResponseEntity<List<RatingDTO>> response = controller.getRatingByRaterEmail("test@gmail.com");
		RatingDTO politicianResponse = response.getBody().get(0);
		RatingDTO politicianResponse2 = response.getBody().get(1);
		
		assertThat(politicianResponse.getPolitician().getId().toString(),
				equalTo(listOfPoliticiansRating.get(0).getPolitician().getPoliticianNumber()));
		assertThat(politicianResponse.getPolitician().getName(),
				equalTo(listOfPoliticiansRating.get(0).getPolitician().getFirstName() + " " + listOfPoliticiansRating.get(0).getPolitician().getLastName()));
		assertThat(politicianResponse2.getPolitician().getId().toString(),
				equalTo(listOfPoliticiansRating.get(1).getPolitician().getPoliticianNumber()));
		assertThat(politicianResponse2.getPolitician().getName(),
				equalTo(listOfPoliticiansRating.get(1).getPolitician().getFirstName() + " " + listOfPoliticiansRating.get(1).getPolitician().getLastName()));
	}
	
	@Test
	public void assertEqualsUserRaterDtoOutputs() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		when(assembler.toModel(ratingDTO)).thenReturn(EntityModel.of(ratingDTO));
		
		EntityModel<RatingDTO> response = controller.getRatingById("1").getBody();
		
		assertThat(response.getContent().getRater().getFacebookName(),
				equalTo(politiciansRating.getRater().getFacebookName()));
		assertThat(response.getContent().getRater().getPoliticalParties().toString(),
				equalTo(politiciansRating.getRater().getPoliticalParties().toString()));
		assertThat(response.getContent().getRater().getEmail(),
				equalTo(politiciansRating.getRater().getEmail()));
	}
	
	@Test
	public void assertEqualsUserRaterListOfDtoOutputs() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		
		when(service.findRatingsByFacebookEmail("test@gmail.com")).thenReturn(listOfPoliticiansRating);

		ResponseEntity<List<RatingDTO>> response = controller.getRatingByRaterEmail("test@gmail.com");
		
		RatingDTO politicianResponse = response.getBody().get(0);
		RatingDTO politicianResponse2 = response.getBody().get(1);
		
		assertThat(politicianResponse.getRater().getEmail(), 
				equalTo(listOfPoliticiansRating.get(0).getRater().getEmail()));
		assertThat(politicianResponse.getRater().getFacebookName(), 
				equalTo(listOfPoliticiansRating.get(0).getRater().getFacebookName()));
		assertThat(politicianResponse.getRater().getPoliticalParties().toString(), 
				equalTo(listOfPoliticiansRating.get(0).getRater().getPoliticalParties().toString()));
		assertThat(politicianResponse2.getRater().getEmail(), 
				equalTo(listOfPoliticiansRating.get(1).getRater().getEmail()));
		assertThat(politicianResponse2.getRater().getFacebookName(), 
				equalTo(listOfPoliticiansRating.get(1).getRater().getFacebookName()));
		assertThat(politicianResponse2.getRater().getPoliticalParties().toString(), 
				equalTo(listOfPoliticiansRating.get(1).getRater().getPoliticalParties().toString()));
	}
	
}