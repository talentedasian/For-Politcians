package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public record AverageRating(double rating) {

    private static final double MINIMUM = 0;
    private static final int MAXIMUM = 10;

    public AverageRating {
        Assert.state(isRatingGreaterThanMinimum(rating), "rating must not be less than 0");
        maximumRatingValidation(rating);
    }

    private void maximumRatingValidation(double rating) {
        if (!isRatingLessThanMaximum(rating)) throw new AverageRatingHasExceededMaximumValueException(rating);
    }

    private boolean isRatingLessThanMaximum(double rating) {
        return rating < MAXIMUM;
    }

    private boolean isRatingGreaterThanMinimum(double rating) {
        return rating >= MINIMUM;
    }

    public static AverageRating of(BigDecimal averageRating) {
        return new AverageRating(averageRating.doubleValue());
    }

    public static AverageRating of(BigDecimal totalScoreAccumulated, int count, AverageRating previousAverageRating) {
        if (isTotalScoreAccumulatedZero(totalScoreAccumulated)) throw new IllegalStateException("""
                total rating accumulated must be greater than 0
                """);
        if (isCountZero(count)) throw new IllegalStateException("count must be greater than 0");

        double averageRatingCalculated = Rating.mapToSatisfactionRate(previousAverageRating.averageRating())
                .calculate(totalScoreAccumulated, count);

        return of(BigDecimal.valueOf(averageRatingCalculated));
    }

    private static boolean isCountZero(int count) {
        return count == 0;
    }

    private static boolean isTotalScoreAccumulatedZero(BigDecimal totalScoreAccumulated) {
        return totalScoreAccumulated.compareTo(BigDecimal.ZERO) == 0;
    }

    public double averageRating() {
        return this.rating;
    }

}
