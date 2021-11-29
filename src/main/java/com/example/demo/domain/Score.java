package com.example.demo.domain;

import org.springframework.util.Assert;

/**
 * A score is the rating that a user has for a politician e.g. 2.131
 */
public record Score(double rating) {

    static final double MINIMUM = 0.1;
    static final int MAXIMUM = 10;

    public Score {
        Assert.state(isRatingGreaterThanMinimum(rating), "score must be greater than 0");
        if (!isRatingLessThanMaximum(rating)) throw new ScoreHasExceededMaximumValueException();
    }

    private boolean isRatingLessThanMaximum(double rating) {
        return rating < MAXIMUM;
    }

    private boolean isRatingGreaterThanMinimum(double rating) {
        return rating >= MINIMUM;
    }

    public static Score of(double rating) {
        return new Score(rating);
    }

    public String ratingExact() {
        return String.valueOf(rating);
    }
}
