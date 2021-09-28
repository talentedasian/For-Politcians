package com.example.demo.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.demo.domain.AverageRating.NO_RATING_YET;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("Domain")
public class TotalRatingAccumulatedTest {

    @Test
    public void shouldThrowIllegalStateExceptionWhenTotalRatingPassedIsNegative() throws Exception{
        BigDecimal negativeTotalRatingAccumulated = valueOf(-2231321);

        assertThatThrownBy(() -> TotalRatingAccumulated.of(negativeTotalRatingAccumulated, NO_RATING_YET))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot contain a negative value");
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenTotalRatingPassedIsNull() throws Exception{
        BigDecimal nullTotalRatingAccumulated = null;

        assertThatThrownBy(() -> TotalRatingAccumulated.of(nullTotalRatingAccumulated, NO_RATING_YET))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Total rating accumulated cannot be null");
    }

    @Test
    public void shouldReturnExpectedTotalRatingBasedOnCurrentAverageRating() throws Exception{
        BigDecimal EXPECTED_CALCULATED_TOTAL_RATING_WITH_LOW_AVERAGE_RATING = valueOf(2.325);

        TotalRatingAccumulated totalRatingAccumulated = TotalRatingAccumulated.of(valueOf(2.3242), AverageRating.of(valueOf(3.434)));

        assertThat(totalRatingAccumulated.totalRating())
                .isEqualTo(EXPECTED_CALCULATED_TOTAL_RATING_WITH_LOW_AVERAGE_RATING);
    }

    @Test
    public void shouldReturnTotalRatingWithOnly3DigitsIfDigitsExceedMaximumAndIsNotALowRating() throws Exception{
        BigDecimal EXPECTED_CALCULATED_TOTAL_RATING_WITH_LOW_AVERAGE_RATING = valueOf(9.992);

        TotalRatingAccumulated totalRatingAccumulated = TotalRatingAccumulated.of(valueOf(9.9923), NO_RATING_YET);

        assertThat(totalRatingAccumulated.totalRating())
                .isEqualTo(EXPECTED_CALCULATED_TOTAL_RATING_WITH_LOW_AVERAGE_RATING);
    }

}
