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

import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingsControllerJwtTest extends BaseSpringHateoasTest {

    AddRatingDTORequest objectRequestContent = new AddRatingDTORequest(BigDecimal.ONE, "1", "DDS");
    String requestContent;

    {
        try {
            requestContent = new ObjectMapper().writeValueAsString(objectRequestContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturn401UnauthorizedWhenJwtIsNotPresent() throws Exception{
        mvc.perform(post(create("/api/ratings/rating"))
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())

                    .andExpect(jsonPath("code", equalTo("401")))
                    .andExpect(jsonPath("err", containsStringIgnoringCase("no jwt found")));
    }

    @Test
    public void shouldReturn401UnAuthorizedWhenJwtIsExpiredAndCannotBeAutomaticallyRefreshed() throws Exception{
        LocalDate cannotBeRefreshedExpiredJwt = LocalDate.now().minusDays(9);
        String id = ACC_NUMBER().accountNumber();
        String jwt = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate("test@gmail.com", id, cannotBeRefreshedExpiredJwt);

        mvc.perform(post(create("/api/ratings/rating"))
                        .header("Authorization", "Bearer " + jwt)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())

                    .andExpect(jsonPath("code", equalTo("401")))
                    .andExpect(jsonPath("err", containsStringIgnoringCase("is expired")));
    }

    @Test
    public void shouldReturn401UnAuthorizedWhenAuthorizationHeaderValueDoesNotStartWithBearer() throws Exception{
        String id = ACC_NUMBER().accountNumber();
        String jwt = JwtJjwtProviderAdapater.createJwtWithDynamicExpirationDate("test@gmail.com", id, LocalDate.now());

        mvc.perform(post(create("/api/ratings/rating"))
                        .header("Authorization", "B " + jwt)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())

                    .andExpect(jsonPath("code", equalTo("401")))
                    .andExpect(jsonPath("err", containsStringIgnoringCase("must start with Bearer")));
    }

}
