package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefreshExpiredJwtTest extends BaseSpringHateoasTest {

    AddRatingDTORequest objectRequestContent = new AddRatingDTORequest(BigDecimal.ONE, "1", "DDS");
    String requestContent;

    {
        try {
            requestContent = new ObjectMapper().writeValueAsString(objectRequestContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    final String ID = ACC_NUMBER().accountNumber();
    final String SUB = "test@gmail.com";
    final String NAME = "Random Name";

    @Test
    public void shouldReturn401UnauthorizedForExpiredJwtWhenJwtIsNotRefreshable() throws Exception{
        var expiredAndNotRefreshableJwtDate = LocalDate.now().minusDays(1);

        String jwt = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate(SUB, ID, expiredAndNotRefreshableJwtDate);

        mvc.perform(post(create("/api/ratings/rating"))
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())

                .andExpect(jsonPath("code", equalTo("401")))
                .andExpect(jsonPath("err", containsStringIgnoringCase("is expired")));
    }

    @Test
    public void shouldGiveNewJwtInCookieWhenJwtIsExpiredButStillRefreshable() throws Exception{
        LocalDateTime expiredButRefreshableJwt = LocalDateTime.now().minusMinutes(43);
        String jwt = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate(SUB, ID, expiredButRefreshableJwt);

        mvc.perform(post(create("/api/ratings/rating"))
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
