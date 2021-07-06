package com.example.demo.baseClasses;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class AbstractPoliticianControllerTest {

	@Mock
	public PoliticiansService service;
	@Mock
	public AverageCalculator calculator;
	
	public PoliticianController controller;
	public Politicians politician;
	public AddPoliticianDTORequest politicianDTORequest;
	
	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service);
		
		politician =  new Politicians();
		politician.setRating(new Rating(9.67D, 9.67D, calculator));
		politician.setFirstName("Mirriam");
		politician.setLastName("Defensor");
		politician.setPoliticianNumber("123PolNumber");
		
		politicianDTORequest = new AddPoliticianDTORequest
				("Mirriam", 
				"Defensor",
				BigDecimal.valueOf(9.67D));
	}
	
	public static Politicians withoutRepo(String firstName, String lastName,
			List<PoliticiansRating> politiciansRating, Rating rating) {
		var politician = new Politicians();
		politician.setFirstName(firstName);
		politician.setLastName(lastName);
		politician.setPoliticiansRating(politiciansRating);
		politician.setRating(rating);
		
		return politician;
	}
	
	public static Politicians withRepoAndId(RatingRepository repo, Integer id, 
			String firstName, String lastName,
			List<PoliticiansRating> politiciansRating, Rating rating, String polNumber) {
		var politician = new Politicians();
		politician.setRepo(repo);
		politician.setId(id);
		politician.setFirstName(firstName);
		politician.setLastName(lastName);
		politician.setPoliticiansRating(politiciansRating);
		politician.setRating(rating);
		politician.setPoliticianNumber(polNumber);
		
		return politician;
	}
	
}
