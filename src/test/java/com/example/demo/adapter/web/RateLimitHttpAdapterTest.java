package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.MediaTypes;

import java.time.LocalDate;

import static com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 TESTS USES IN MEMORY FOR NOW SINCE INDIVIDUAL TESTS DO NOT ROLLBACK
 THE ENTITIES BEING SAVED IN THE DATABASE
 */

public class RateLimitHttpAdapterTest extends BaseSpringHateoasTest {

    @Autowired RateLimitRepository rateLimitRepository;

    @Test
    public void shouldReturn200OkWithSelfAndPoliticianLink() throws Exception{
        AccountNumber ACCOUNT_NUMBER = ACC_NUMBER();
        PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
        rateLimitRepository.save(new RateLimit(ACCOUNT_NUMBER.accountNumber(), POLITICIAN_NUMBER, LocalDate.now()));

        String jwt = createJwtWithFixedExpirationDate("test@gmail.com", ACCOUNT_NUMBER.accountNumber(), "random name");

        mvc.perform(get(create("/rate-limit/" + POLITICIAN_NUMBER.politicianNumber()))
                    .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON))

                    .andDo(document("self", links(halLinks(),
                            linkWithRel("self").description("Link that points where the rate limit can be found"),
                            linkWithRel("politician").description("Link that points to the politician that this rate limit is on"))));
    }

    @Test
    public void shouldReturnHalFormTemplateDefaultWhereTargetLinkIsTheLinkToRatePoliticiansWhenNotRateLimited() throws Exception{
        AccountNumber ACCOUNT_NUMBER = ACC_NUMBER();
        PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
        rateLimitRepository.save(new RateLimit(ACCOUNT_NUMBER.accountNumber(), POLITICIAN_NUMBER, LocalDate.now().minusDays(9)));

        String jwt = createJwtWithFixedExpirationDate("test@gmail.com", ACCOUNT_NUMBER.accountNumber(), "random name");

        String linkToRateAPolitician = "/api/ratings/rating";
        mvc.perform(get(create("/rate-limit/" + POLITICIAN_NUMBER.politicianNumber()))
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_FORMS_JSON))

                    .andExpect(jsonPath("_templates.default", notNullValue()))
                    .andExpect(jsonPath("_templates.default.target", containsStringIgnoringCase(linkToRateAPolitician)));
    }

    @Test
    public void shouldReturn401UnauthorizedWhenJwtIsNotPresent() throws Exception{
        mvc.perform(get(create("/rate-limit/" + POL_NUMBER().politicianNumber())))
                .andExpect(status().isUnauthorized())

                    .andExpect(jsonPath("code", equalTo("401")))
                    .andExpect(jsonPath("err", equalToIgnoringCase("no jwt found from authorization header")))
                    .andExpect(jsonPath("additional_information", containsStringIgnoringCase("requires a jwt to be present")));
    }

    @TestConfiguration
    static class Configuration {
        @Bean
        @Primary
        public RateLimitRepository inMemory() {
            return new InMemoryRateLimitRepository();
        }
    }

}
