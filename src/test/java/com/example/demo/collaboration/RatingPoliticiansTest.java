package com.example.demo.collaboration;

import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.DefaultRateLimitDomainService;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.InMemoryRatingAdapterRepo;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.entities.Politicians;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingPoliticiansTest {

    final String FIRST_NAME = "Rodrigo";
    final String LAST_NAME = "Duterte";

    PoliticiansRepository polRepo;
    RateLimitRepository rateLimitRepo;
    RatingRepository ratingRepo;

    RatingService ratingService;

    Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(NumberTestFactory.POL_NUMBER())
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPoliticiansRating(null)
            .setRating(new Rating(0D, 0D));

    @BeforeEach
    public void setup() {
        polRepo = new InMemoryPoliticianAdapterRepo();

        rateLimitRepo = new InMemoryRateLimitRepository();

        ratingRepo = new InMemoryRatingAdapterRepo();

        ratingService = new RatingService(ratingRepo, polRepo, new DefaultRateLimitDomainService(rateLimitRepo));
    }

    @Test
    public void shouldReturnExpectedCalculatedRatingAverageWhenMultipleRatersRateASinglePolitician() throws Exception {
        final double EXPECTED_CALCULATED_AVERAGE_RATING = 4.965D;

        var politician = new PresidentialBuilder(politicianBuilder).build();

        polRepo.save(politician);

        var rater = createRater(ACC_NUMBER().accountNumber());
        var secondRater = createRater(ACC_NUMBER().accountNumber() + "1");
        var thirdRater = createRater(ACC_NUMBER().accountNumber() + "2");
        var fourthRater = createRater(ACC_NUMBER().accountNumber() + "3");

        var firstRatingMadeByFirstRater = createPolRating(5D, rater, politician);
        var firstRatingMadeBySecondRater = createPolRating(2.31223D, secondRater, politician);
        var firstRatingMadeByThirdRater = createPolRating(4.32D, thirdRater, politician);
        var firstRatingMadeByFourthRater = createPolRating(8.22433D, fourthRater, politician);

        ratingService.saveRatings(firstRatingMadeByFirstRater);
        ratingService.saveRatings(firstRatingMadeBySecondRater);
        ratingService.saveRatings(firstRatingMadeByThirdRater);
        ratingService.saveRatings(firstRatingMadeByFourthRater);

        var politicianQueried = polRepo.findByPoliticianNumber(politician.retrievePoliticianNumber()).get();

        assertThat(politicianQueried.averageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void averageRatingOfPoliticianShouldBeWhatTheSoleRatingIsWhenPoliticianHasOnlyOneRater() throws Exception{
        final double EXPECTED_CALCULATED_AVERAGE_RATING = 4.977D;

        var politician = new PresidentialBuilder(politicianBuilder).build();

        polRepo.save(politician);

        var rater = createRater(ACC_NUMBER().accountNumber());

        var rating = createPolRating(4.97654D, rater, politician);

        ratingService.saveRatings(rating);

        var politicianQueried = polRepo.findByPoliticianNumber(politician.retrievePoliticianNumber()).get();

        assertThat(politicianQueried.averageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void averageRatingOfPoliticianShouldBeToWhatWasInitiallySetDuringInstanceCreationWhenItDoesNotHaveRaters() throws Exception{
        final double EXPECTED_AVERAGE_RATING = politicianBuilder.build().averageRating();

        var politician = new PresidentialBuilder(politicianBuilder).build();

        polRepo.save(politician);

        var politicianQueried = polRepo.findByPoliticianNumber(politician.retrievePoliticianNumber()).get();

        assertThat(politicianQueried.averageRating())
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

}
