package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.PresidentialDtoMapper;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianControllerTest {

	@Mock PoliticiansService service;
	@Mock AverageCalculator calculator;
	@Mock PoliticianAssembler assembler;
	
	PoliticianController controller;
	Politicians politician;
	AddPoliticianDTORequest politicianDTORequest;
	
	final String POLITICIAN_NUMBER = "123polNumber";
	
	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service, assembler);
		
		politician =  new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER) 
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
	
	@Test
	public void shouldCallSaveFromServiceWhenSaved() {
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		controller.savePolitician(politicianDTORequest);
		
		verify(service, times(1)).savePolitician(politicianDTORequest);
	}
	
	@Test
	public void shouldEqualDTOOutputsWhenSavedForPresidential() {
		var presidentialPolitician = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politician)
				.setMostSignificantLawPassed("Rice Law")
				.build();
		PoliticianDTO polDTO = new PresidentialDtoMapper().mapToDTO(presidentialPolitician);
		
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = Objects.requireNonNull(controller.savePolitician(politicianDTORequest).getBody()).getContent();
		assertEquals(polDTO, politicianResponse);
	}

	@Test
	public void shouldEqualDTOOutputsWhenSavedForSenatorial() {
		var presidentialPolitician = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politician)
				.setTotalMonthsOfService(12)
				.setMostSignificantLawMade("Rice Law")
				.build();
		PoliticianDTO polDTO = new PresidentialDtoMapper().mapToDTO(presidentialPolitician);

		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));

		PoliticianDTO politicianResponse = controller.savePolitician(politicianDTORequest).getBody().getContent();
		assertEquals(polDTO, politicianResponse);
	}
	
	@Test
	public void shouldEqualDTOOutputsByPoliticianNumber() {
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		when(service.findPoliticianByNumber(POLITICIAN_NUMBER)).thenReturn(politician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.politicianById(POLITICIAN_NUMBER).getBody().getContent();
		
		assertEquals(polDTO, politicianResponse);
	}			
	
}
