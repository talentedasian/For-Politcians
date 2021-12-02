package com.example.demo.domain;

import org.springframework.util.Assert;

/**
 * A score is the rating that a user has for a politician e.g. 2.131
 */
public record Score(String rating) {

    // MINIMUM is actually 0.1
    static final int MINIMUM = 0;
    static final int MAXIMUM = 10;

    public Score {
        Assert.state(isOnMin(rating), "score must be greater than 0");
        if (!isOnMax(rating)) throw new ScoreHasExceededMaximumValueException();
    }

    private boolean isOnMax(String rating) {
        try {
            return Integer.parseInt(rating) <= MAXIMUM;
        } catch(NumberFormatException e) {
            if (Integer.parseInt(rating.substring(0,1)) <= MAXIMUM) return true;
            return !rating.substring(0,2).contains(".");
        }
    }

    private boolean isOnMin(String rating) {
        if (rating.contains("-")) return false;
        return Integer.parseInt(rating.substring(0,1)) > MINIMUM;
    }

    public static Score of(String rating) {
        return new Score(rating);
    }
}
