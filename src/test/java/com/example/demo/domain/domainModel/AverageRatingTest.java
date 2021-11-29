package com.example.demo.domain.domainModel;

import com.example.demo.domain.AverageRating;
import com.example.demo.domain.AverageRatingHasExceededMaximumValueException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("Domain")
public class AverageRatingTest {

    @Test
    public void shouldReturnAverageRatingSet() throws Exception{
        BigDecimal VALID_AVERAGE_RATING = valueOf(2.321);
        String averageRating = new AverageRating(VALID_AVERAGE_RATING).averageRating();

        assertThat(averageRating)
                .isEqualTo(VALID_AVERAGE_RATING.toString());
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForHighRating() throws Exception{
        String EXPECTED_AVERAGE_RATING = "8.71";

        AverageRating PREVIOUS_AVERAGE_RATING = new AverageRating(valueOf(9.32131));
        String averageRating = AverageRating.of(9,PREVIOUS_AVERAGE_RATING, "3.231").averageRating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForDecentRating() throws Exception{
        String EXPECTED_AVERAGE_RATING = "6.49";

        AverageRating PREVIOUS_AVERAGE_RATING = new AverageRating(valueOf(6.42));
        String averageRating = AverageRating.of(41, PREVIOUS_AVERAGE_RATING, "9.3122").averageRating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForLowRating() throws Exception{
        String EXPECTED_AVERAGE_RATING = "2.131";

        AverageRating PREVIOUS_AVERAGE_RATING = new AverageRating(valueOf(2.131));
        String averageRating = AverageRating.of(599, PREVIOUS_AVERAGE_RATING, "1.9922").averageRating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldThrowScoreHasExceededMaximumValueExceptionIfAverageRatingExceeds10() throws Exception{
        ThrowableAssert.ThrowingCallable shouldThrowAverageRating =
                () -> new AverageRating(new BigDecimal("100.1"));

        assertThatThrownBy(shouldThrowAverageRating)
                .isInstanceOf(AverageRatingHasExceededMaximumValueException.class);
    }

}
