package com.example.demo.domain;

import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * A score is the rating that a user has for a politician e.g. 2.131
 */
public record Score(BigDecimal rating) {

    static final float MINIMUM = 0.000001f;
    static final int MAXIMUM = 10;

    private static boolean isRatingLessThanMaximum(String rating) {
        return (rating.charAt(1) == '.') ? Integer.parseInt(rating.substring(0,1)) < MAXIMUM : false;
    }

    private static boolean isRatingGreaterThanMinimum(String rating) {
        if (rating.contains("-"))
            return false;

        if (rating.charAt(0) == '0') {
            if (rating.charAt(1) != '.') {
                return false;
            }
            try {
                return Float.parseFloat(rating) >= MINIMUM;
            } catch(NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

    private static boolean isValidNumber(String num) {
        try {
            Float.parseFloat(num);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static Score of(String rating) {
        Assert.state(isValidNumber(rating), "score must be a valid non-rational or rational number");
        Assert.state(isRatingGreaterThanMinimum(rating), "score must be greater than 0");
        if (!isRatingLessThanMaximum(rating)) throw new ScoreHasExceededMaximumValueException();
        return new Score(new BigDecimal(rating));
    }
}
