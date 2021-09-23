package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.baseClasses.FakeDomainService;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.enums.PoliticalParty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static com.example.demo.domain.enums.PoliticalParty.GREY_ZONE;
import static java.math.BigDecimal.valueOf;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RateLimitedRatingPoliticianHttpAdapterTest extends BaseSpringHateoasTest {

    @Autowired
    RatingRepository ratingRepo;
    @Autowired
    PoliticiansRepository polRepo;
    @Autowired UserRateLimitService rateLimitService;

    PoliticianTypes.PresidentialPolitician politician = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(new Politicians.PoliticiansBuilder(POL_NUMBER())
            .setFirstName("Fake")
            .setRating(new Rating(0D, 0D)))
            .build();

    UserRater rater = new UserRater.Builder()
            .setName("Fake")
            .setAccountNumber(ACC_NUMBER().accountNumber())
            .setEmail("test@gmail.com")
            .setPoliticalParty(PoliticalParty.DDS)
            .build();
    PoliticiansRating politiciansRating = new PoliticiansRating.Builder(politician)
            .setRater(rater)
            .setRating(2d)
            .build();

    @Autowired
    UserRateLimitService service;

    @Test
    public void shouldReturn429WhenJwtIsValidAndIsCurrentlyRateLimited() throws Exception{
        polRepo.save(politician);

        var requestObject = new AddRatingDTORequest(valueOf(9.21D), politician.retrievePoliticianNumber(), GREY_ZONE.toString());
        String requestJsonString = new ObjectMapper().writeValueAsString(requestObject);

        String jwt = createJwtWithFixedExpirationDate("t@gmail.com", ACC_NUMBER().accountNumber(), "Jake");

        mvc.perform(post(create("/api/ratings/rating"))
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isTooManyRequests())

                    .andExpect(jsonPath("err", containsStringIgnoringCase("can rate again after 7 days")))
                    .andExpect(jsonPath("code", equalTo("429")));
    }

    @TestConfiguration
    static class Config{
        @Bean
        @Primary
        UserRateLimitService noRateService() {
            return FakeDomainService.noRateService();
        }
    }

}

