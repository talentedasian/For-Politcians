package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public record AverageRating(BigDecimal rating) {

    public static final AverageRating NO_RATING_YET = null;

    private static final BigDecimal MINIMUM = new BigDecimal("0.01");
    private static final BigDecimal MAXIMUM = BigDecimal.TEN;

    public AverageRating {
        Assert.state(isRatingGreaterThanMinimum(rating), "rating must not be less than 0.01");
        maximumRatingValidation(rating);
    }

    private void maximumRatingValidation(BigDecimal rating) {
        if (!isRatingLessThanMaximum(rating)) throw new AverageRatingHasExceededMaximumValueException(rating.toString());
    }

    private boolean isRatingLessThanMaximum(BigDecimal rating) {
        return rating.compareTo(MAXIMUM) < 0;
    }

    private boolean isRatingGreaterThanMinimum(BigDecimal rating) {
        return rating.compareTo(MINIMUM) > 0;
    }

    public static AverageRating of(String averageRating) {
        return new AverageRating(new BigDecimal(averageRating));
    }

    public static AverageRating of(BigDecimal totalScoreAccumulated, int count, AverageRating previousAverageRating) {
        if (isTotalScoreAccumulatedZero(totalScoreAccumulated))
            throw new IllegalStateException("total rating accumulated must be greater than 0");
        if (isCountZero(count))
            throw new IllegalStateException("count must be greater than 0");

        BigDecimal averageRatingCalculated = calculateAverageRating(totalScoreAccumulated, count, previousAverageRating);

        return new AverageRating(averageRatingCalculated);
    }

    private static BigDecimal calculateAverageRating(BigDecimal totalScoreAccumulated, int count, AverageRating previousAverageRating) {
        return determineRatingClassification(previousAverageRating).calculate(totalScoreAccumulated, count);
    }

    private static Rating determineRatingClassification(AverageRating previousAverageRating) {
        return Rating.mapToSatisfactionRate(previousAverageRating.rating().doubleValue());
    }

    private static boolean isCountZero(int count) {
        return count == 0;
    }

    private static boolean isTotalScoreAccumulatedZero(BigDecimal totalScoreAccumulated) {
        return totalScoreAccumulated.compareTo(BigDecimal.ZERO) == 0;
    }

    public String averageRating() {
        return this.rating.toString();
    }

    public boolean isAverageRatingLow() {
        return Rating.mapToSatisfactionRate(rating.doubleValue()).equals(Rating.LOW);
    }

    public static boolean hasRating(AverageRating averageRating) {
        return !Objects.equals(averageRating, NO_RATING_YET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AverageRating that = (AverageRating) o;

        return rating != null ? rating.compareTo(that.rating) == 0 : that.rating == null;
    }

    @Override
    public int hashCode() {
        return rating != null ? rating.hashCode() : 0;
    }
}
