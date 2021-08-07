package com.example.demo.controller;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.repository.PoliticiansRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.demo.model.enums.Rating.mapToSatisfactionRate;
import static java.net.URI.create;
import static org.mockito.Mockito.mock;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PoliticianSavingTest extends BaseClassTestsThatUsesDatabase {

    MockMvc mvc;

    Politicians.PoliticiansBuilder politiciansBuilder;
    PoliticianTypes.PresidentialPolitician.PresidentialBuilder presidentialBuilder;

    @Autowired PoliticiansRepository repo;

    final String presidentialDTORequest = """
            {
                "first_name" : "Test",
                "last_name" : "Name",
                "rating" : 0.00,
                "type" : "Presidential"
            }
            """;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .build();

        politiciansBuilder = new Politicians.PoliticiansBuilder("dummy")
                .setFirstName("Rodrigo")
                .setLastName("Duterte")
                .setFullName()
                .setRating(new Rating(0D, 0D, mock(LowSatisfactionAverageCalculator.class)));

        presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politiciansBuilder);
    }

    @Test
    public void testIfSavingSimplyWorksWithAllThePolymorphismWithJackson() throws Exception {
        var president = presidentialBuilder.setBuilder(politiciansBuilder
                        .setFirstName("Test")
                        .setLastName("Name")
                        .setFullName())
                .build();

        this.mvc.perform(post(create("/api/politicians/politician"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(presidentialDTORequest)
                        .accept(HAL_FORMS_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(president.getPoliticianNumber()))
                .andExpect(jsonPath("name").value(president.getFullName()))
                .andExpect(jsonPath("rating").value(president.getRating().getAverageRating()))
                .andExpect(jsonPath("most_significant_law_signed").value(president.getMostSignificantLawSigned()))
                .andExpect(jsonPath("satisfaction_rate").value(mapToSatisfactionRate(president.getRating().getAverageRating()).toString()));

        repo.delete(president);
    }

}
