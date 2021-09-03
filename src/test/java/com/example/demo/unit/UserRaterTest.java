package com.example.demo.unit;

import com.example.demo.baseClasses.NumberTestFactory;
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

    @Test
    public void shouldReturnNonEmptyOptionalWhenFindingExistentRateLimitOnUser() {
        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        rater.rateLimitUser(POLITICIAN_NUMBER);

        assertThat(rater.findRateLimit(POLITICIAN_NUMBER))
                .isNotEmpty();
    }

    @Test
    public void shouldReturn0IfUserIsNotRateLimit() {
        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        assertThat(rater.daysLeftToRate(POLITICIAN_NUMBER.politicianNumber()))
                .isEqualTo(0);
    }

    @Test
    public void shouldReturn7IfUserJustGotRateLimited() {
        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        rater.rateLimitUser(POLITICIAN_NUMBER);

        assertThat(rater.daysLeftToRate(POLITICIAN_NUMBER.politicianNumber()))
                .isEqualTo(7);
    }

}
