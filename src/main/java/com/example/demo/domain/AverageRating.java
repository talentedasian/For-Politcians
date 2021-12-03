package com.example.demo.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

public record AverageRating(BigDecimal rating) {

    public static final AverageRating NO_RATING_YET = null;

    private static final BigDecimal MINIMUM = BigDecimal.ZERO;
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

    public static AverageRating of(BigDecimal averageRating) {
        return new AverageRating(averageRating);
    }

    public String averageRating() {
        return this.rating.toString();
    }

    public static boolean hasRating(AverageRating averageRating) {
        return !Objects.equals(averageRating, NO_RATING_YET);
    }
}
