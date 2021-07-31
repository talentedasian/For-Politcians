package com.example.demo.unit.controller;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.service.PoliticiansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PoliticianControllerTest {

	@Mock PoliticiansService service;
	@Mock AverageCalculator calculator;
	@Mock PoliticianAssembler assembler;
	
	PoliticianController controller;

	final String POLITICIAN_NUMBER = "123polNumber";
	final BigDecimal AVERAGE_RATING = BigDecimal.valueOf(9.67D);
	PoliticiansBuilder politicianBuilder;
	SenatorialBuilder senatorialBuilder;
	AddPoliticianDTORequest politicianDTORequest;

	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service, assembler);

		politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setRating(new Rating(9.67D, AVERAGE_RATING.doubleValue(), calculator))
				.setFirstName("Mirriam")
				.setLastName("Defensor")
				.setFullName();

		senatorialBuilder = new SenatorialBuilder(politicianBuilder).setTotalMonthsOfService(12).setMostSignificantLawMade("Anti Terror");
		
		politicianDTORequest = new AddPoliticianDTORequest
				("Mirriam", 
				"Defensor",
				AVERAGE_RATING);
	}
	
	@Test
	public void shouldCallSaveFromServiceWhenSaved() {
		when(service.savePolitician(politicianDTORequest)).thenReturn(senatorialBuilder.build());
		controller.savePolitician(politicianDTORequest);
		
		verify(service, times(1)).savePolitician(politicianDTORequest);
	}
	
	@Test
	public void shouldEqualDTOOutputsWhenSavedForPresidential() {
		var presidentialPolitician = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
				.setMostSignificantLawPassed("Rice Law")
				.build();
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(presidentialPolitician);
		
		when(service.savePolitician(politicianDTORequest)).thenReturn(senatorialBuilder.setTotalMonthsOfService(12).build());
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = Objects.requireNonNull(controller.savePolitician(politicianDTORequest).getBody()).getContent();
		assertEquals(polDTO, politicianResponse);
	}

	@Test
	public void shouldEqualDTOOutputsWhenSavedForSenatorial() {
		Politicians senatorialPolitician = senatorialBuilder.build();
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(senatorialPolitician);

		when(service.savePolitician(politicianDTORequest)).thenReturn(senatorialPolitician);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));

		PoliticianDTO politicianResponse = controller.savePolitician(politicianDTORequest).getBody().getContent();
		assertEquals(polDTO, politicianResponse);
	}
	
	@Test
	public void shouldEqualDTOOutputsByPoliticianNumber() {
		PoliticianTypes.SenatorialPolitician senatorial = senatorialBuilder.setTotalMonthsOfService(31).build();
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(senatorial);
		
		when(service.findPoliticianByNumber(POLITICIAN_NUMBER)).thenReturn(senatorial);
		when(assembler.toModel(any())).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.politicianById(POLITICIAN_NUMBER).getBody().getContent();
		
		assertEquals(polDTO, politicianResponse);
	}			
	
}
