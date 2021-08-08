package com.example.demo.integration.controllers;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.repository.PoliticiansRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.example.demo.model.enums.Rating.mapToSatisfactionRate;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PolymorphicQueriesTest extends BaseClassTestsThatUsesDatabase {

    MockMvc mvc;

    @Autowired PoliticiansRepository repo;

    Politicians.PoliticiansBuilder politiciansBuilder;
    PoliticianTypes.SenatorialPolitician.SenatorialBuilder senatorialBuilder;
    PoliticianTypes.PresidentialPolitician.PresidentialBuilder presidentialBuilder;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .build();

        politiciansBuilder = new Politicians.PoliticiansBuilder("dummy")
                .setFirstName("Rodrigo")
                .setLastName("Duterte")
                .setFullName()
                .setRating(new Rating(1D, 1D));

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
        var senator = senatorialBuilder.build();
        List<Politicians> polymorphicPoliticians = List.of(president,senator);

        repo.saveAll(polymorphicPoliticians);


        this.mvc.perform(get(create("/api/politicians/politicians"))
                        .accept(HAL_FORMS_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_FORMS_JSON))
                .andExpect(jsonPath("_embedded.senatorialPoliticianDTOList[0].id", equalTo(senator.getPoliticianNumber())))
                .andExpect(jsonPath("_embedded.senatorialPoliticianDTOList[0].name", equalTo(senator.getFullName())))
                .andExpect(jsonPath("_embedded.senatorialPoliticianDTOList[0].rating", equalTo(senator.getRating().getAverageRating())))
                .andExpect(jsonPath("_embedded.senatorialPoliticianDTOList[0].months_of_service", equalTo(senator.getTotalMonthsOfServiceAsSenator())))
                .andExpect(jsonPath("_embedded.senatorialPoliticianDTOList[0].most_significant_law_made", equalTo(senator.getMostSignificantLawMade())))
                .andExpect(jsonPath("_embedded.presidentialPoliticianDTOList[0].id", equalTo(president.getPoliticianNumber())))
                .andExpect(jsonPath("_embedded.presidentialPoliticianDTOList[0].name", equalTo(president.getFullName())))
                .andExpect(jsonPath("_embedded.presidentialPoliticianDTOList[0].rating", equalTo(president.getRating().getAverageRating())))
                .andExpect(jsonPath("_embedded.presidentialPoliticianDTOList[0].most_significant_law_signed", equalTo(president.getMostSignificantLawSigned())))
                .andExpect(jsonPath("_embedded.presidentialPoliticianDTOList[0].satisfaction_rate", equalTo(mapToSatisfactionRate(president.getRating().getAverageRating()).toString())));

            /*
             * We are dealing with a real database here. Delete the entities
             * before the test finishes.
             */
            repo.deleteAll(polymorphicPoliticians);
        }

}
