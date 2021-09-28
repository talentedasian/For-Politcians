package com.example.demo.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.example.demo.domain.AverageRating.NO_RATING_YET;

public class TotalRatingAccumulated {

    public static final TotalRatingAccumulated ZERO = TotalRatingAccumulated.of(BigDecimal.valueOf(0));
    private static final int MAXIMUM_DECIMAL_DIGITS = 3;

    private final BigDecimal totalRating;
    private final AverageRating averageRating;

    private TotalRatingAccumulated(BigDecimal totalRating, AverageRating averageRating) {
        Assert.state(totalRating != null, "Total rating accumulated cannot be null");
        if (isTotalRatingNegative(totalRating)) throw new IllegalStateException("Total rating accumulated cannot contain a negative value");

        this.totalRating = totalRating;
        this.averageRating = averageRating;
    }

    private boolean isTotalRatingNegative(BigDecimal totalRating) {
        return totalRating.signum() == -1;
    }

    public static TotalRatingAccumulated of(BigDecimal totalRating) {
        return new TotalRatingAccumulated(totalRating, NO_RATING_YET);
    }

    public static TotalRatingAccumulated of(final BigDecimal totalRating, AverageRating averageRating) {
        BigDecimal rating = (hasRating(averageRating) && averageRating.isAverageRatingLow())
                ? alwaysRoundIfRatingIsLow(totalRating)
                : cutOffDecimalDigitsWithNoRounding(totalRating);
        return new TotalRatingAccumulated(rating, averageRating);
    }

    private static BigDecimal cutOffDecimalDigitsWithNoRounding(BigDecimal totalRating) {
        return totalRating.setScale(MAXIMUM_DECIMAL_DIGITS, RoundingMode.FLOOR);
    }

    private static BigDecimal alwaysRoundIfRatingIsLow(BigDecimal totalRating) {
        return totalRating.setScale(MAXIMUM_DECIMAL_DIGITS, RoundingMode.UP);
    }

    private static boolean hasRating(AverageRating averageRating) {
        return !(Objects.equals(averageRating, NO_RATING_YET));
    }

    public TotalRatingAccumulated addTotalRating(Score scoreToAdd) {
        return of(BigDecimal.valueOf(scoreToAdd.rating()).add(totalRating), averageRating);
    }

    public BigDecimal totalRating() {
        return this.totalRating;
    }
}
