package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public record AverageRating(BigDecimal rating) {

    public static final AverageRating NO_RATING_YET = null;
    public static final AverageRating ONE = new AverageRating(BigDecimal.ONE);

    private static final double MINIMUM = 0.01;
    private static final int MAXIMUM = 10;

    public AverageRating {
        Assert.state(isRatingGreaterThanMinimum(rating.doubleValue()), "rating must not be less than 0.01");
        maximumRatingValidation(rating.doubleValue());
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

    public static AverageRating of(String newSummand) {
        return new AverageRating(new BigDecimal(newSummand));
    }

    public static AverageRating of(int count, AverageRating previousAverageRating, String newSummand) {
        String averageRatingCalculated = calculateAverageRating(count, previousAverageRating, newSummand);

        return new AverageRating(new BigDecimal(averageRatingCalculated));
    }

    private static String calculateAverageRating(int count, AverageRating previousAverageRating, String newSummand) {
        return determineRatingClassification(previousAverageRating).calculate(previousAverageRating.averageRating(), count, newSummand);
    }

    private static Rating determineRatingClassification(AverageRating previousAverageRating) {
        return hasRating(previousAverageRating)
                ? Rating.mapToSatisfactionRate(previousAverageRating.rating().doubleValue())
                : Rating.LOW;
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
}
