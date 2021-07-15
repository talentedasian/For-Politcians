package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianControllerTest {

	@Mock PoliticiansService service;
	@Mock AverageCalculator calculator;
	@Mock PoliticianAssembler assembler;
	
	PoliticianController controller;
	Politicians politician;
	AddPoliticianDTORequest politicianDTORequest;
	
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
	
	@Test
	public void shouldCallSaveFromServiceWhenSaved() {
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		controller.savePolitician(politicianDTORequest);
		
		verify(service, times(1)).savePolitician(politicianDTORequest);
	}
	
	@Test
	public void shouldEqualDTOOutputsWhenSaved() {
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.savePolitician(politicianDTORequest).getBody().getContent();
		
		assertEquals(polDTO, politicianResponse);
	}		
	
	@Test
	public void shouldEqualDTOOutputsByPoliticianNumber() {
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		when(service.findPoliticianByNumber("1")).thenReturn(politician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.politicianById("1").getBody().getContent();
		
		assertEquals(polDTO, politicianResponse);
	}			
	
}
