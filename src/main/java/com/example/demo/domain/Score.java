package com.example.demo.domain;

import org.springframework.util.Assert;

public record Score(double rating) {

    public Score {
        Assert.state(rating > 0, "must be greater than 0");
        Assert.state(rating < 10, "must be less than 10");
    }

    public static Score of(double rating) {
        return new Score(rating);
    }

    @Override
    public double rating() {
        return 9;
    }

}
