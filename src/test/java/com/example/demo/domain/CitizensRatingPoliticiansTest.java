package com.example.demo.domain;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculator;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/*
    Citizens refer to basically the users who rate politicians
 */
@ExtendWith(SpringExtension.class)
public class CitizensRatingPoliticiansTest {

    PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.PRESIDENTIAL);
    final String POLITICIAN_NUMBER  = polNumberCalculator.calculatePoliticianNumber(new Name("random", "name")).politicianNumber();

    PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
            .setPoliticiansRating(null)
            .setFirstName("Random")
            .setLastName("Name")
            .setFullName()
            .setRating(new Rating(0D, 0D));

    Politicians politicians;

    @Mock RateLimitRepository rateLimitRepo;

    @BeforeEach
    public void setup() {
        politicians = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder).build();
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalled() throws UserRateLimitedOnPoliticianException {
        Double EXPECTED_CALCULATED_AVERAGE_RATING = 2.734D;

        var rater = new UserRater.Builder()
                .setAccountNumber(NumberTestFactory.ACC_NUMBER().accountNumber())
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();
        var raterThatsNotRateLimited = new UserRater.Builder()
                .setAccountNumber("FLPOM-00003123")
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
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
                .setRater(raterThatsNotRateLimited)
                .setPolitician(politicians)
                .build();

        firstRating.ratePolitician();
        fourScaledRatingForHalfDownRoundingMode.ratePolitician();

        assertThat(politicians.getRating().getAverageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void countsOfRatingsShouldReflectOnPoliticianAsCitizensRatePoliticians() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = new UserRater.Builder()
                .setAccountNumber(NumberTestFactory.ACC_NUMBER().accountNumber())
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var raterThatsNotRateLimited = new UserRater.Builder()
                .setAccountNumber("FLPOM-00003123")
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var firstRating = new PoliticiansRating.Builder()
                .setRating(2.243D)
                .setRepo(rateLimitRepo)
                .setRater(rater)
                .setPolitician(politicians)
                .build();
        var secondRating = new PoliticiansRating.Builder()
                .setRating(3.22326D)
                .setRepo(rateLimitRepo)
                .setRater(raterThatsNotRateLimited)
                .setPolitician(politicians)
                .build();

        firstRating.ratePolitician();
        secondRating.ratePolitician();

        assertThat(politicians.countsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

    @Test
    public void totalCountsOfRatingsShouldStillBe2WhenRaterDeletesPolitician() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = new UserRater.Builder()
                .setAccountNumber(NumberTestFactory.ACC_NUMBER().accountNumber())
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var raterThatsNotRateLimited = new UserRater.Builder()
                .setAccountNumber("FLPOM-00003123")
                .setName("Random Name")
                .setEmail("test@gmail.com")
                .setPoliticalParty(PoliticalParty.DDS)
                .setRateLimit(null)
                .build();

        var firstRating = new PoliticiansRating.Builder()
                .setRating(2.243D)
                .setRepo(rateLimitRepo)
                .setRater(rater)
                .setPolitician(politicians)
                .build();
        var secondRating = new PoliticiansRating.Builder()
                .setRating(3.22326D)
                .setRepo(rateLimitRepo)
                .setRater(raterThatsNotRateLimited)
                .setPolitician(politicians)
                .build();

        firstRating.ratePolitician();
        secondRating.ratePolitician();

        secondRating.deleteRating();

        assertThat(politicians.countsOfRatings())
                .isEqualTo(1);
        assertThat(politicians.totalCountsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

}
