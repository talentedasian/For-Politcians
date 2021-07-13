package com.example.demo.baseClasses;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class AbstractPoliticianControllerTest {

	@Mock
	public PoliticiansService service;
	@Mock
	public AverageCalculator calculator;
	@Mock
	public PoliticianAssembler assembler;
	
	public PoliticianController controller;
	public Politicians politician;
	public AddPoliticianDTORequest politicianDTORequest;
	
	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service, assembler);
		
		politician =  new Politicians.PoliticiansBuilder("123polNumber")
			.setRating(new Rating(9.67D, 9.67D, calculator))
			.setFirstName("Mirriam")
			.setLastName("Defensor")
			.setFullName()
			.build();
		
		politicianDTORequest = new AddPoliticianDTORequest
				("Mirriam", 
				"Defensor",
				BigDecimal.valueOf(9.67D));
	}
	
}
