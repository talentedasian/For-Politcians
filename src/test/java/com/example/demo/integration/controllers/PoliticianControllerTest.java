package com.example.demo.integration.controllers;

import com.example.demo.controller.PoliticianController;
import com.example.demo.exceptionHandling.GlobalExceptionHandling;
import com.example.demo.exceptionHandling.PoliticianExceptionHandling;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.service.PoliticiansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.demo.model.enums.Rating.mapToSatisfactionRate;
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
	SenatorialBuilder senatorialBuilder;
	PresidentialBuilder presidentialBuilder;

	PoliticianAssembler modelMaker = new PoliticianAssembler();

	final String content = """
				{
					"first_name" : "Test",
					"last_name" : "Name",
					"rating": 1.00,
					"type" : "Senatorial",
					"months_of_service" : 31  
				}
				""";

	@BeforeEach
	public void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(new PoliticianController(polService, modelMaker))
				.setControllerAdvice(new GlobalExceptionHandling())
				.setControllerAdvice(new PoliticianExceptionHandling())
				.alwaysDo(print())
				.build();

		politiciansBuilder = new Politicians.PoliticiansBuilder("123polNumber")
			.setId(123)
			.setFirstName("Mirriam")
			.setLastName("Defensor")
			.setFullName()
			.setRating(new Rating(1D, 1D, mock(LowSatisfactionAverageCalculator.class)));

		senatorialBuilder = new SenatorialBuilder(politiciansBuilder).setTotalMonthsOfService(12);

		presidentialBuilder = new PresidentialBuilder(politiciansBuilder).setMostSignificantLawPassed("Any Law");
	}

	@Test
	public void shouldEqualDtoOutputsByPoliticianNumberByPresidential() throws Exception {
		var actualPolitician = presidentialBuilder.build();

		when(polService.findPoliticianByNumber("123polNumber")).thenReturn(actualPolitician);
		
		this.mvc.perform(get(create("/api/politicians/politician/123polNumber"))
				.accept(HAL_FORMS_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(actualPolitician.getFullName())))
				.andExpect(jsonPath("id", equalTo(actualPolitician.getPoliticianNumber())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(mapToSatisfactionRate(actualPolitician.getRating().getAverageRating()).toString())))
				.andExpect(jsonPath("most_significant_law_signed", equalTo(actualPolitician.getMostSignificantLawSigned())));
	}

	@Test
	public void shouldEqualDtoOutputsByPoliticianNumberBySenatorial() throws Exception {
		var actualPolitician = senatorialBuilder.build();

		when(polService.findPoliticianByNumber("123polNumber")).thenReturn(actualPolitician);

		this.mvc.perform(get(create("/api/politicians/politician/123polNumber"))
						.accept(HAL_FORMS_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(actualPolitician.getFullName())))
				.andExpect(jsonPath("id", equalTo(actualPolitician.getPoliticianNumber())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(mapToSatisfactionRate(actualPolitician.getRating().getAverageRating()).toString())))
				.andExpect(jsonPath("most_significant_law_made", equalTo(actualPolitician.getMostSignificantLawMade())))
				.andExpect(jsonPath("months_of_service", equalTo(actualPolitician.getTotalMonthsOfServiceAsSenator())));
	}
	
	@Test
	public void shouldEqualDtoOutputsWhenSavedByPresidential() throws Exception {
		var actualPolitician = presidentialBuilder.build();

		when(polService.savePolitician(any())).thenReturn(actualPolitician);

		this.mvc.perform(post(create("/api/politicians/politician/"))
				.content(content)
				.contentType(APPLICATION_JSON)
				.accept(HAL_FORMS_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(actualPolitician.getFullName())))
				.andExpect(jsonPath("id", equalTo(actualPolitician.getPoliticianNumber())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(mapToSatisfactionRate(actualPolitician.getRating().getAverageRating()).toString())))
				.andExpect(jsonPath("most_significant_law_signed", equalTo(actualPolitician.getMostSignificantLawSigned())));
	}

	@Test
	public void shouldEqualDtoOutputsWhenSavedBySenatorial() throws Exception {
		var actualPolitician = senatorialBuilder.build();

		when(polService.savePolitician(any())).thenReturn(actualPolitician);

		this.mvc.perform(post(create("/api/politicians/politician"))
						.content(content)
						.contentType(APPLICATION_JSON)
						.accept(HAL_FORMS_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(HAL_FORMS_JSON))
				.andExpect(jsonPath("name", equalTo(actualPolitician.getFullName())))
				.andExpect(jsonPath("id", equalTo(actualPolitician.getPoliticianNumber())))
				.andExpect(jsonPath("rating", equalTo(1.0D)))
				.andExpect(jsonPath("satisfaction_rate", equalTo(mapToSatisfactionRate(actualPolitician.getRating().getAverageRating()).toString())))
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