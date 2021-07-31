package com.example.demo.integration.controllers;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.service.PoliticiansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PoliticianController.class)
public class PoliticianControllerTest {

	MockMvc mvc;

	@MockBean PoliticiansService polService;
	@MockBean PoliticianAssembler assembler;
	
	Politicians.PoliticiansBuilder politiciansBuilder;
	PoliticiansRating politiciansRating;
	PoliticianDTO politicianDTO;
	EntityModel<PoliticianDTO> entityModel;

	SenatorialBuilder senatorialBuilder;
	PresidentialBuilder presidentialBuilder;

	PoliticiansDtoMapper mapper = new PoliticiansDtoMapper();
	PoliticianAssembler modelMaker = new PoliticianAssembler();

	final String content = """
				{
					"firstName": "Test",
					"lastName": "Name",
					"rating": 1.00
				}
				""";

	@BeforeEach
	public void setup(WebApplicationContext wac) {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac)
				.alwaysDo(print())
				.build();

		politiciansBuilder = new Politicians.PoliticiansBuilder("123polNumber")
			.setId(123)
			.setFirstName("Mirriam")
			.setLastName("Defensor")
			.setPoliticianNumber("123polNumber")
			.setRating(new Rating(1D, 1D, mock(LowSatisfactionAverageCalculator.class)));

		senatorialBuilder = new SenatorialBuilder(politiciansBuilder).setTotalMonthsOfService(12);

		presidentialBuilder = new PresidentialBuilder(politiciansBuilder).setMostSignificantLawPassed("Any Law");
	}

	@Test
	public void shouldEqualDtoOutputsByPoliticianNumberByPresidential() throws Exception {
		var actualPolitician = presidentialBuilder.build();

		politicianDTO = mapper.mapToDTO(actualPolitician);
		entityModel = modelMaker.toModel(politicianDTO);

		when(polService.findPoliticianByNumber("123polNumber")).thenReturn(actualPolitician);
		when(assembler.toModel(any())).thenReturn(entityModel);
		
		this.mvc.perform(get(create("/api/politicians/politician/123polNumber"))
				.accept(HAL_FORMS_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(politicianDTO.getName())))
				.andExpect(jsonPath("id", equalTo(politicianDTO.getId())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(politicianDTO.getSatisfactionRate().toString())))
				.andExpect(jsonPath("most_significant_law_signed", equalTo(actualPolitician.getMostSignificantLawSigned())));
	}

	@Test
	public void shouldEqualDtoOutputsByPoliticianNumberBySenatorial() throws Exception {
		var actualPolitician = senatorialBuilder.build();

		politicianDTO = mapper.mapToDTO(actualPolitician);
		entityModel = modelMaker.toModel(politicianDTO);

		when(polService.findPoliticianByNumber("123polNumber")).thenReturn(actualPolitician);
		when(assembler.toModel(any())).thenReturn(entityModel);

		this.mvc.perform(get(create("/api/politicians/politician/123polNumber"))
						.accept(HAL_FORMS_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(politicianDTO.getName())))
				.andExpect(jsonPath("id", equalTo(politicianDTO.getId())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(politicianDTO.getSatisfactionRate().toString())))
				.andExpect(jsonPath("most_significant_law_made", equalTo(actualPolitician.getMostSignificantLawMade())))
				.andExpect(jsonPath("months_of_service", equalTo(actualPolitician.getTotalMonthsOfServiceAsSenator())));
	}
	
	@Test
	public void shouldEqualDtoOutputsWhenSavedByPresidential() throws Exception {
		var actualPolitician = presidentialBuilder.build();

		politicianDTO = mapper.mapToDTO(actualPolitician);
		entityModel = modelMaker.toModel(politicianDTO);

		when(polService.savePolitician(any())).thenReturn(actualPolitician);
		when(assembler.toModel(any())).thenReturn(entityModel);

		this.mvc.perform(post(create("/api/politicians/politician/"))
				.content(content)
				.contentType(APPLICATION_JSON)
				.accept(HAL_FORMS_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(politicianDTO.getName())))
				.andExpect(jsonPath("id", equalTo(politicianDTO.getId())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(politicianDTO.getSatisfactionRate().toString())))
				.andExpect(jsonPath("most_significant_law_signed", equalTo(actualPolitician.getMostSignificantLawSigned())));
	}

	@Test
	public void shouldEqualDtoOutputsWhenSavedBySenatorial() throws Exception {
		var actualPolitician = senatorialBuilder.build();

		politicianDTO = mapper.mapToDTO(actualPolitician);
		entityModel = modelMaker.toModel(politicianDTO);

		when(polService.savePolitician(any())).thenReturn(actualPolitician);
		when(assembler.toModel(any())).thenReturn(entityModel);

		this.mvc.perform(post(create("/api/politicians/politician/"))
						.content(content)
						.contentType(APPLICATION_JSON)
						.accept(HAL_FORMS_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(politicianDTO.getName())))
				.andExpect(jsonPath("id", equalTo(politicianDTO.getId())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(politicianDTO.getSatisfactionRate().toString())))
				.andExpect(jsonPath("most_significant_law_made", equalTo(actualPolitician.getMostSignificantLawMade())))
				.andExpect(jsonPath("months_of_service", equalTo(actualPolitician.getTotalMonthsOfServiceAsSenator())));
	}
	
	@Test
	public void shouldExpectPoliticianNotFoundWhenDeleting() throws Exception {
		when(polService.deletePolitician("123polNumber")).thenReturn(false);
		
		this.mvc.perform(delete(create("/api/politicians/politician/123polNumber")))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldExpectPoliticianNoContentWhenDeleting() throws Exception {
		when(polService.deletePolitician("123polNumber")).thenReturn(true);
		
		this.mvc.perform(delete(create("/api/politicians/politician/123polNumber")))
			.andExpect(status().isNoContent());
	}
	
}