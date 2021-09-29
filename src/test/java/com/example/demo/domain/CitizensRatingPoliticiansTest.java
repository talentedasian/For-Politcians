package com.example.demo.domain;

import com.example.demo.baseClasses.FakeDomainService;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.entities.*;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculator;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

/*
    Citizens refer to basically the users who rate politicians
 */
@ExtendWith(SpringExtension.class)
@Tag("Domain")
public class CitizensRatingPoliticiansTest {

    PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.PRESIDENTIAL);
    final PoliticianNumber POLITICIAN_NUMBER  = polNumberCalculator.calculatePoliticianNumber(new Name("random", "name"));

    PoliticiansBuilder politicianBuilder;

    Politicians politicians;

    UserRateLimitService fakeDomainService = FakeDomainService.unliRateService();

    @BeforeEach
    public void setup() {
        politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName("Random")
                .setLastName("Name")
                .setTotalRating(ZERO);

        politicians = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder).build();
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalled() throws UserRateLimitedOnPoliticianException {
        double EXPECTED_CALCULATED_AVERAGE_RATING = 2.733;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());
        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(Score.of(2.243), rater, politicians);
        var fourScaledRatingForHalfDownRoundingMode = createPolRating(Score.of(3.22326), raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician(fakeDomainService);
        fourScaledRatingForHalfDownRoundingMode.ratePolitician(fakeDomainService);

        assertThat(politicians.averageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void ratingShouldBeCalculatedAsExpectedWhenRatePoliticianCalledMoreThan2Times() throws UserRateLimitedOnPoliticianException {
        double EXPECTED_CALCULATED_AVERAGE_RATING = 3.897;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());
        var raterThatsNotRateLimited = createRater("FLPOM-00003123");
        var secondRaterThatsNotRateLimited = createRater("FLPOM-000120312");

        var firstRating = createPolRating(Score.of(2.243), rater, politicians);
        var fourScaledRating = createPolRating(Score.of(3.22326), raterThatsNotRateLimited, politicians);
        var threeScaledRating = createPolRating(Score.of(6.223), secondRaterThatsNotRateLimited, politicians);

        firstRating.ratePolitician(fakeDomainService);
        fourScaledRating.ratePolitician(fakeDomainService);
        threeScaledRating.ratePolitician(fakeDomainService);

        assertThat(politicians.averageRating())
                .isEqualTo(EXPECTED_CALCULATED_AVERAGE_RATING);
    }

    @Test
    public void countsOfRatingsShouldReflectOnPoliticianAsCitizensRatePoliticians() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(Score.of(2.243), rater, politicians);
        var secondRating = createPolRating(Score.of(3.22326), raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician(fakeDomainService);
        secondRating.ratePolitician(fakeDomainService);

        assertThat(politicians.countsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

    @Test
    public void totalCountsOfRatingsShouldStillBe2WhenRaterDeletesPolitician() throws UserRateLimitedOnPoliticianException {
        int EXPECTED_NUMBER_OF_RATINGS = 2;

        var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

        var raterThatsNotRateLimited = createRater("FLPOM-00003123");

        var firstRating = createPolRating(Score.of(2.243), rater, politicians);
        var secondRating = createPolRating(Score.of(3.2232), raterThatsNotRateLimited, politicians);

        firstRating.ratePolitician(fakeDomainService);
        secondRating.ratePolitician(fakeDomainService);

        secondRating.deleteRatingOnPolitician();

        assertThat(politicians.countsOfRatings())
                .isEqualTo(1);
        assertThat(politicians.totalCountsOfRatings())
                .isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
    }

}
