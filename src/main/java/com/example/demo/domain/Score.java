package com.example.demo.domain;

import org.springframework.util.Assert;

public class Score {

    private final double rating;

    Score(double rating) {
        Assert.state(rating > 0, "must be greater than 0");
        Assert.state(rating < 10, "must be less than 10");
        this.rating = rating;
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
