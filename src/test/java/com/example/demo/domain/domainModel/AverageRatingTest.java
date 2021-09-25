package com.example.demo.domain.domainModel;

import com.example.demo.domain.AverageRating;
import com.example.demo.domain.AverageRatingHasExceededMaximumValueException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AverageRatingTest {

    @Test
    public void shouldReturnAverageRatingSet() throws Exception{
        BigDecimal VALID_AVERAGE_RATING = valueOf(2.3214);
        double averageRating = AverageRating.of(VALID_AVERAGE_RATING).rating();

        assertThat(averageRating)
                .isEqualTo(VALID_AVERAGE_RATING.doubleValue());
    }

    @Test
    public void shouldScoreHasExceededMaximumValueException() throws Exception{
        ThrowableAssert.ThrowingCallable shouldThrowAverageRating = () -> AverageRating.of(valueOf(2131));

        assertThatThrownBy(shouldThrowAverageRating)
                .isInstanceOf(AverageRatingHasExceededMaximumValueException.class);
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForHighRating() throws Exception{
        double EXPECTED_AVERAGE_RATING = 5.70;

        AverageRating PREVIOUS_AVERAGE_RATING = AverageRating.of(valueOf(9.32131));
        BigDecimal TOTAL_RATING_ACCUMULATED = valueOf(51.321);
        double averageRating = AverageRating.of(TOTAL_RATING_ACCUMULATED, 9, PREVIOUS_AVERAGE_RATING).rating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForDecentRating() throws Exception{
        double EXPECTED_AVERAGE_RATING = 7.84;

        AverageRating PREVIOUS_AVERAGE_RATING = AverageRating.of(valueOf(6.42));
        BigDecimal TOTAL_RATING_ACCUMULATED = valueOf(321.3234);
        double averageRating = AverageRating.of(TOTAL_RATING_ACCUMULATED, 41, PREVIOUS_AVERAGE_RATING).rating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldReturnExpectedAverageRatingCalculatedWithSpecifiedParametersForLowRating() throws Exception{
        double EXPECTED_AVERAGE_RATING = 4.827;

        AverageRating PREVIOUS_AVERAGE_RATING = AverageRating.of(valueOf(2.131));
        BigDecimal TOTAL_RATING_ACCUMULATED = valueOf(2891.23123);
        double averageRating = AverageRating.of(TOTAL_RATING_ACCUMULATED, 599, PREVIOUS_AVERAGE_RATING).rating();

        assertThat(averageRating)
                .isEqualTo(EXPECTED_AVERAGE_RATING);
    }

    @Test
    public void shouldReturn0IfTotalRatingAccumulatedIs0() throws Exception{
        BigDecimal ZERO_RATING_ACCUMULATED = BigDecimal.ZERO;

        assertThatThrownBy(() -> AverageRating.of(ZERO_RATING_ACCUMULATED, 0, AverageRating.of(valueOf(1d))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("must be greater than 0");
    }

    @Test
    public void shouldReturn0IfCountIs0() throws Exception{
        int zeroCount = 0;

        assertThatThrownBy(() -> AverageRating.of(BigDecimal.TEN, zeroCount, AverageRating.of(valueOf(1d))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("count must be greater than 0");
    }

    @Test
    public void shouldThrowScoreHasExceededMaximumValueExceptionIfAverageRatingExceeds10() throws Exception{
        BigDecimal TOTAL_RATING_ACCUMULATED = valueOf(2891.23123);

        ThrowableAssert.ThrowingCallable shouldThrowAverageRating =
                () -> AverageRating.of(TOTAL_RATING_ACCUMULATED, 200, AverageRating.of(valueOf(2.131))).rating();


        assertThatThrownBy(shouldThrowAverageRating)
                .isInstanceOf(AverageRatingHasExceededMaximumValueException.class);
    }

}
