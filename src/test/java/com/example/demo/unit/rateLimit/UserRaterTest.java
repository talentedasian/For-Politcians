package com.example.demo.unit.rateLimit;

import com.example.demo.domain.NumberTestFactory;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.PoliticianNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRaterTest {

    final PoliticianNumber POLITICIAN_NUMBER = NumberTestFactory.POL_NUMBER();
    final String ACCOUNT_NUMBER = NumberTestFactory.ACC_NUMBER().accountNumber();

    @Test
    public void shouldReturnFalseIfUserCurrentlyHasRateLimitOnPolitician() {
        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        rater.rateLimitUser(POLITICIAN_NUMBER);
        assertThat(rater.canRate(POLITICIAN_NUMBER.politicianNumber()))
                .isEqualTo(false);
    }

}
