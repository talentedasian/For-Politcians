package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RatingApplicationServiceTest {

    RateLimitRepository repo;

    RateLimitingService service;

    @BeforeEach
    public void setup() {
        repo = new InMemoryRateLimitRepository();

        service = new RateLimitingService(repo);
    }

    @Test
    public void shouldOverwritePreviousRateLimitWhenSavingRateLimit() {
        final String ID = "1";

        var outdatedRateLimit = service.rateLimitUser(ID, NumberTestFactory.POL_NUMBER());

        Optional<RateLimit> shouldBeDeletedRateLimit = repo.findUsingIdAndPoliticianNumber("1", NumberTestFactory.POL_NUMBER());

        assertThat(shouldBeDeletedRateLimit)
                .isNotEmpty()
                .get()
                .isEqualTo(outdatedRateLimit);

        var overwritingRateLimit = service.rateLimitUser(ID, NumberTestFactory.POL_NUMBER());

        Optional<RateLimit> shouldOverwriteRateLimit = repo.findUsingIdAndPoliticianNumber("1", NumberTestFactory.POL_NUMBER());

        assertThat(shouldOverwriteRateLimit)
                .isNotEmpty()
                .get()
                .isEqualTo(overwritingRateLimit);

        assertThat(repo.countUsingIdAndPoliticianNumber(ID, NumberTestFactory.POL_NUMBER()))
                .isEqualTo(1);
    }

}
