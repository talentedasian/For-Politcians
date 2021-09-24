package com.example.demo.domain;

import org.springframework.util.Assert;

public class Score {

    static final int MINIMUM = 0;
    static final int MAXIMUM = 10;

    private final double rating;

    Score(double rating) {
        Assert.state(isRatingGreaterThanMinimum(rating), "must be greater than 0");
        if (!isRatingLessThanMaximum(rating)) throw new ScoreHasExceededMaximumValueException();
        this.rating = rating;
    }

    private boolean isRatingLessThanMaximum(double rating) {
        return rating < MAXIMUM;
    }

    private boolean isRatingGreaterThanMinimum(double rating) {
        return rating > MINIMUM;
    }

    public static Score of(double rating) {
        return new Score(rating);
    }

    public double rating() {
        return this.rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        return Double.compare(score.rating, rating) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(rating);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Score = " + rating;
    }
}
