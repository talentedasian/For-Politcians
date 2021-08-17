package com.example.demo.domain;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/*
    Citizens refer to basically the users who rate politicians
 */
@ExtendWith(SpringExtension.class)
public class CitizensRatingPoliticiansTest {

    Politicians politicians;

    @Mock RateLimitRepository rateLimitRepo;

    @BeforeEach
    public void setup() {
        politicians = new PoliticiansBuilder("dummy")
                .setPoliticiansRating(null)
                .setFirstName("Random")
                .setLastName("Name")
                .setFullName()
                .setRating(new Rating(0D, 0D))
                .build();
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalled() {
        Double EXPECTED_CALCULATED_AVERAGE_RATING = 2.734D;

        var rater = new UserRater.Builder()
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .build();


        var firstRating = new PoliticiansRating.Builder()
                .setRating(2.243D)
                .setRepo(rateLimitRepo)
                .setRater(rater)
                .setPolitician(politicians)
                .build();
        var fourScaledRatingForHalfDownRoundingMode = new PoliticiansRating.Builder()
                .setRating(3.22326D)
                .setRepo(rateLimitRepo)
                .setRater(rater)
                .setPolitician(politicians)
                .build();

        when(rateLimitRepo.save(ArgumentMatchers.any(RateLimit.class))).thenReturn(null);

        firstRating.ratePolitician();
        fourScaledRatingForHalfDownRoundingMode.ratePolitician();

        assertThat(politicians.getRating().getAverageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

}
