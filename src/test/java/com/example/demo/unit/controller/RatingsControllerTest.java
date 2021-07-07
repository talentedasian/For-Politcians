package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.CollectionModel;
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
import com.example.demo.service.RateLimitingService;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class RatingsControllerTest {
	
	@Mock RatingService service;
	@Mock RatingAssembler assembler;
	@Mock RateLimitingService limitingService;
	
	RatingsController controller;
	
	Politicians politician;
	PoliticiansRating politiciansRating;
	RatingDTO ratingDTO;
	PoliticianDTO politicianDTO;	
	final UserRater userRater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accountNumber", limitingService);
	
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
	public void assertEqualsDtoOutputsById() throws Exception {
		when(service.findById("1")).thenReturn(politiciansRating);
		when(assembler.toModel(ratingDTO)).thenReturn(EntityModel.of(ratingDTO));
		
		ResponseEntity<EntityModel<RatingDTO>> response = controller.getRatingById("1");
		
		RatingDTO expected = response.getBody().getContent();
		
		assertEquals(expected, ratingDTO);
	}
	
	@Test
	public void assertEqualsPoliticiansListOfDtoOutputsByEmail() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		CollectionModel<EntityModel<RatingDTO>> expected = new RatingAssembler().toCollectionModel(new RatingDtoMapper().mapToDTO(listOfPoliticiansRating));
		
		when(service.findRatingsByFacebookEmail("test@gmail.com")).thenReturn(listOfPoliticiansRating);
		when(assembler.toCollectionModel(any())).thenReturn(expected);
		
		CollectionModel<EntityModel<RatingDTO>> response = controller.getRatingByRaterEmail("test@gmail.com").getBody();
		
		
		assertEquals(expected, response);
	}
	
	@Test
	public void assertEqualsPoliticiansListOfDtoOutputsByAccNumber() throws Exception {
		var politiciansRating2 = new PoliticiansRating(1, 0.01D, userRater, politician);
		List<PoliticiansRating> listOfPoliticiansRating = List.of(politiciansRating, politiciansRating2);
		CollectionModel<EntityModel<RatingDTO>> expected = new RatingAssembler().toCollectionModel(new RatingDtoMapper().mapToDTO(listOfPoliticiansRating));
				
		when(service.findRatingsByAccountNumber("123accNumber")).thenReturn(listOfPoliticiansRating);
		when(assembler.toCollectionModel(any())).thenReturn(expected);
		
		CollectionModel<EntityModel<RatingDTO>> response = controller.getRatingByRaterAccountNumber("123accNumber").getBody();
		
		assertEquals(expected, response);
	}
	
}