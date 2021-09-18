package com.example.demo.collaboration;

import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.Rating;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RatingPoliticiansTest {

    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    RateLimitRepository rateLimitRepo;

    DefaultRateLimitDomainService defaultRateLimitDomainService;

    Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    @BeforeEach
    public void setup() {
        rateLimitRepo = new InMemoryRateLimitRepository();

        defaultRateLimitDomainService = new DefaultRateLimitDomainService(rateLimitRepo);
    }

    @Test
    public void throwsUserRateLimitedExceptionWhenUserAlreadyRatedPoliticianWithin7Days() throws Exception{
        var politician = new PresidentialBuilder(politicianBuilder).build();

        var rater = createRater(ACC_NUMBER().accountNumber());

        var rating = createPolRating(4.97654D, rater, politician);

        rating.ratePolitician(defaultRateLimitDomainService);

        assertThatThrownBy(() -> rating.ratePolitician(defaultRateLimitDomainService))
                .isInstanceOf(UserRateLimitedOnPoliticianException.class)
                .hasMessageContaining("can rate again after 7 days");
    }

    @Test
    public void raterCantRateAfterRatingPolitician() throws Exception{
        var politician = new PresidentialBuilder(politicianBuilder).build();

        var rater = createRater(ACC_NUMBER().accountNumber());

        var rating = createPolRating(4.97654D, rater, politician);

        rating.ratePolitician(defaultRateLimitDomainService);

        assertThat(rater.canRate(defaultRateLimitDomainService, PoliticianNumber.of(politician.retrievePoliticianNumber())))
                .isFalse();
    }

}
