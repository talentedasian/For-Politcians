package com.example.demo.domain;

import com.example.demo.adapter.out.repository.InMemoryRateLimitRepository;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculator;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static org.assertj.core.api.Assertions.assertThat;

/*
    Citizens refer to basically the users who rate politicians
 */
@ExtendWith(SpringExtension.class)
public class CitizensRatingPoliticiansTest {

    PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.PRESIDENTIAL);
    final PoliticianNumber POLITICIAN_NUMBER  = polNumberCalculator.calculatePoliticianNumber(new Name("random", "name"));

    PoliticiansBuilder politicianBuilder;

    Politicians politicians;

    RateLimitRepository rateLimitRepo;

    @BeforeEach
    public void setup() {
        politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName("Random")
                .setLastName("Name")
                .setFullName()
                .setRating(new Rating(0D, 0D));

        politicians = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder).build();

        rateLimitRepo = new InMemoryRateLimitRepository();
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalled() throws UserRateLimitedOnPoliticianException {
        Double EXPECTED_CALCULATED_AVERAGE_RATING = 2.734D;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());
        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(2.243, rater, politicians);
        var fourScaledRatingForHalfDownRoundingMode = createPolRating(3.22326, raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician((accountNumber, politicianNumber) -> true);
        fourScaledRatingForHalfDownRoundingMode.ratePolitician((accountNumber, politicianNumber) -> true);

        assertThat(politicians.getRating().getAverageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalledMoreThan2Times() throws UserRateLimitedOnPoliticianException {
        Double EXPECTED_CALCULATED_AVERAGE_RATING = 3.897D;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());
        var raterThatsNotRateLimited = createRater("FLPOM-00003123");
        var secondRaterThatsNotRateLimited = createRater("FLPOM-000120312");

        var firstRating = createPolRating(2.243, rater, politicians);
        var fourScaledRating = createPolRating(3.22326, raterThatsNotRateLimited, politicians);
        var threeScaledRating = createPolRating(6.223, secondRaterThatsNotRateLimited, politicians);

        firstRating.ratePolitician((accountNumber, politicianNumber) -> true);
        fourScaledRating.ratePolitician((accountNumber, politicianNumber) -> true);
        threeScaledRating.ratePolitician((accountNumber, politicianNumber) -> true);

        assertThat(politicians.getRating().getAverageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void countsOfRatingsShouldReflectOnPoliticianAsCitizensRatePoliticians() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(2.243, rater, politicians);
        var secondRating = createPolRating(3.22326, raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician((accountNumber, politicianNumber) -> true);
        secondRating.ratePolitician((accountNumber, politicianNumber) -> true);

        assertThat(politicians.countsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

    @Test
    public void totalCountsOfRatingsShouldStillBe2WhenRaterDeletesPolitician() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(2.243, rater, politicians);
        var secondRating = createPolRating(3.2232, raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician((accountNumber, politicianNumber) -> true);
        secondRating.ratePolitician((accountNumber, politicianNumber) -> true);

        secondRating.deleteRating();

        assertThat(politicians.countsOfRatings())
                .isEqualTo(1);
        assertThat(politicians.totalCountsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

}
