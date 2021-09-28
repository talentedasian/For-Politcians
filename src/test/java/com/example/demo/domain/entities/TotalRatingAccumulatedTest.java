package com.example.demo.domain.entities;

import com.example.demo.domain.AverageRating;
import com.example.demo.domain.TotalRatingAccumulated;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("Domain")
public class TotalRatingAccumulatedTest {

    @Test
    public void shouldReturnValidTotalRating() throws Exception{
        BigDecimal totalRatingAccumulated = BigDecimal.TEN;

        assertThat(TotalRatingAccumulated.of(totalRatingAccumulated).totalRating())
                .isEqualTo(totalRatingAccumulated);
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfTotalRatingPassedIsNegative() throws Exception{
        BigDecimal negativeTotalRatingAccumulated = valueOf(-98231);

        assertThatThrownBy(() -> TotalRatingAccumulated.of(negativeTotalRatingAccumulated))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Total rating accumulated cannot contain a negative value");
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

        TotalRatingAccumulated totalRatingAccumulated = TotalRatingAccumulated.of(valueOf(9.9923), AverageRating.NO_RATING_YET);

        assertThat(totalRatingAccumulated.totalRating())
                .isEqualTo(EXPECTED_CALCULATED_TOTAL_RATING_WITH_LOW_AVERAGE_RATING);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenTotalRatingPassedIsNull() throws Exception{
        BigDecimal nullTotalRatingAccumulated = null;

        assertThatThrownBy(() -> TotalRatingAccumulated.of(nullTotalRatingAccumulated))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Total rating accumulated cannot be null");
    }

}
