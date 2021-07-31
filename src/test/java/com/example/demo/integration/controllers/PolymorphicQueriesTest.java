package com.example.demo.integration.controllers;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.exceptionHandling.PoliticianExceptionHandling;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.service.PoliticiansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.model.enums.Rating.mapToSatisfactionRate;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PoliticianController.class)
public class PolymorphicQueriesTest {

    MockMvc mvc;

    @MockBean PoliticiansService service;
    @MockBean PoliticianAssembler assembler;

    Politicians.PoliticiansBuilder politiciansBuilder;
    PoliticianTypes.SenatorialPolitician.SenatorialBuilder senatorialBuilder;
    PoliticianTypes.PresidentialPolitician.PresidentialBuilder presidentialBuilder;

    PoliticiansDtoMapper mapper = new PoliticiansDtoMapper();
    PoliticianAssembler modelMaker = new PoliticianAssembler();

    final String presidentialContent = """
				{
					"first_name" : "Rodrigo",
					"last_name" : "Duterte",
					"rating": 1.00,
					"type" : "Presidential",
					"most_significant_law_signed" : "Banned Public Cigarette"  
				}
				""";
    final String senatorialContent = """
				{
					"first_name" : "Nancy",
					"last_name" : "Binay",
					"rating": 1.00,
					"type" : "Senatorial",
					"months_of_service" : 31  
				}
				""";

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new PoliticianController(service, modelMaker))
                .setControllerAdvice(new PoliticianExceptionHandling())
                .alwaysDo(print())
                .build();

        politiciansBuilder = new Politicians.PoliticiansBuilder("123polNumber")
                .setId(123)
                .setFirstName("Rodrigo")
                .setLastName("Duterte")
                .setFullName()
                .setRating(new Rating(1D, 1D, mock(LowSatisfactionAverageCalculator.class)));

        presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politiciansBuilder)
                .setMostSignificantLawPassed("Banned Public Cigarette");

        senatorialBuilder = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politiciansBuilder)
                .setMostSignificantLawMade("Free Tuition Makati")
                .setTotalMonthsOfService(12)
                .setBuilder(politiciansBuilder
                        .setPoliticianNumber("999polNumber")
                        .setFirstName("Nancy")
                        .setLastName("Binay")
                        .setFullName());
    }

    @Test
    public void testPolymorphicQueriesWithDifferentPoliticianTypes() throws Exception {
        var president = presidentialBuilder.build();
        var senator = senatorialBuilder.buildWithDifferentBuilder();

        when(service.allPoliticians()).thenReturn(List.of(senator, president));

        this.mvc.perform(get(create("/api/politicians/politicians"))
                        .accept(HAL_FORMS_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_FORMS_JSON))
                .andExpect(jsonPath("content[0].id", equalTo(senator.getPoliticianNumber())))
                .andExpect(jsonPath("content[0].name", equalTo(senator.getFullName())))
                .andExpect(jsonPath("content[0].rating", equalTo(senator.getRating().getAverageRating())))
                .andExpect(jsonPath("content[0].months_of_service", equalTo(senator.getTotalMonthsOfServiceAsSenator())))
                .andExpect(jsonPath("content[0].most_significant_law_made", equalTo(senator.getMostSignificantLawMade())))
                .andExpect(jsonPath("content[1].id", equalTo(president.getPoliticianNumber())))
                .andExpect(jsonPath("content[1].name", equalTo(president.getFullName())))
                .andExpect(jsonPath("content[1].rating", equalTo(president.getRating().getAverageRating())))
                .andExpect(jsonPath("content[1].most_significant_law_signed", equalTo(president.getMostSignificantLawSigned())))
                .andExpect(jsonPath("content[1].satisfaction_rate", equalTo(mapToSatisfactionRate(president.getRating().getAverageRating()).toString())));
    }

}
