package com.example.demo.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public record AverageRating(double rating) {

    public static final AverageRating NO_RATING_YET = null;
    public static final AverageRating ONE = new AverageRating(1);

    private static final double MINIMUM = 0.01;
    private static final int MAXIMUM = 10;

    public AverageRating {
        Assert.state(isRatingGreaterThanMinimum(rating), "rating must not be less than 0.01");
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

    public double averageRating() {
        return this.rating;
    }

    public static boolean hasRating(AverageRating averageRating) {
        return !Objects.equals(averageRating, NO_RATING_YET);
    }
}
