package com.example.demo.domain;

import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.entities.PoliticianNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRaterTest {

    final PoliticianNumber POLITICIAN_NUMBER = NumberTestFactory.POL_NUMBER();
    final String ACCOUNT_NUMBER = NumberTestFactory.ACC_NUMBER().accountNumber();
    RateLimitRepository rateLimitRepo;

    @Test
    public void shouldReturnFalseIfUserCurrentlyHasRateLimitOnPolitician() {
        var rater = new UserRater.Builder()
                .setAccountNumber(ACCOUNT_NUMBER)
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        rateLimitRepo = new InMemoryRateLimitRepository();

        rater.rateLimitUser(new DefaultRateLimitDomainService(rateLimitRepo), POLITICIAN_NUMBER);

        assertThat(rater.canRate(new DefaultRateLimitDomainService(rateLimitRepo), POLITICIAN_NUMBER))
                .isEqualTo(false);
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

        rateLimitRepo = new InMemoryRateLimitRepository();

        assertThat(rater.daysLeftToRate(new DefaultRateLimitDomainService(rateLimitRepo), POLITICIAN_NUMBER))
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

        rateLimitRepo = new InMemoryRateLimitRepository();

        rater.rateLimitUser(new DefaultRateLimitDomainService(rateLimitRepo), POLITICIAN_NUMBER);

        assertThat(rater.daysLeftToRate(new DefaultRateLimitDomainService(rateLimitRepo), POLITICIAN_NUMBER))
                .isEqualTo(7);
    }

}
