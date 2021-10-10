package com.example.demo.adapter.web;

import com.example.demo.BaseSpringHateoasTest;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.domain.ExpirationZonedDate;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static com.example.demo.adapter.in.web.jwt.JwtUtils.fixedExpirationDate;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RateLimitResponseHttpAdapterTest extends BaseSpringHateoasTest {

    @Autowired RateLimitRepository rateLimitRepository;

    @Test
    public void shouldReturn0AsDaysLeftToRateWhenUserIsNotRateLimited() throws Exception{
        String accountNumber = ACC_NUMBER().accountNumber();

        String jwt = fixedExpirationDate("test@gmail.com", accountNumber, "random");

        mvc.perform(get(create("/rate-limit/" + POL_NUMBER().politicianNumber()))
                        .header("Authorization", "Bearer " + jwt))

                    .andExpect(jsonPath("days_left_to_rate_again", equalTo(0)));
    }

    @Test
    public void shouldReturnCorrectAccountNumberAndPoliticianNumber() throws Exception{
        String accountNumber = ACC_NUMBER().accountNumber();
        PoliticianNumber politicianNumber = POL_NUMBER();
        rateLimitRepository.save(new RateLimit(accountNumber, politicianNumber, ExpirationZonedDate.ofBehind(9)));

        String jwt = fixedExpirationDate("test@gmail.com", accountNumber, "random");

        mvc.perform(get(create("/rate-limit/" + politicianNumber.politicianNumber()))
                        .header("Authorization", "Bearer " + jwt))

                .andExpect(jsonPath("id", equalTo(accountNumber)))
                .andExpect(jsonPath("politician_number", equalTo(politicianNumber.politicianNumber())));
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
