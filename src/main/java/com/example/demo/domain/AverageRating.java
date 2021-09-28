package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public record AverageRating(double rating) {

    public static final AverageRating NO_RATING_YET = null;
    public static final AverageRating ONE = new AverageRating(1);

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
        return of(averageRating, 1, new AverageRating(averageRating.doubleValue()));
    }

    public static AverageRating of(BigDecimal totalScoreAccumulated, int count, AverageRating previousAverageRating) {
        if (isTotalScoreAccumulatedZero(totalScoreAccumulated))
            throw new IllegalStateException("total rating accumulated must be greater than 0");
        if (isCountZero(count))
            throw new IllegalStateException("count must be greater than 0");

        double averageRatingCalculated = calculateAverageRating(totalScoreAccumulated, count, previousAverageRating);

        return new AverageRating(averageRatingCalculated);
    }

    private static double calculateAverageRating(BigDecimal totalScoreAccumulated, int count, AverageRating previousAverageRating) {
        return determineRatingClassification(previousAverageRating).calculate(totalScoreAccumulated, count);
    }

    private static Rating determineRatingClassification(AverageRating previousAverageRating) {
        return Rating.mapToSatisfactionRate(previousAverageRating.averageRating());
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

    public boolean isAverageRatingLow() {
        return Rating.mapToSatisfactionRate(rating).equals(Rating.LOW);
    }

    public static boolean hasRating(AverageRating averageRating) {
        return Objects.equals(averageRating, NO_RATING_YET);
    }
}
