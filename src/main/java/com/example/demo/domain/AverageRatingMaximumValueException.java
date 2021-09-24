package com.example.demo.domain;

public class AverageRatingMaximumValueException extends RuntimeException{

    private static final String defaultMessage = "Average ratings must only be greater than 0 and less than 10";

    public AverageRatingMaximumValueException() {
        super(defaultMessage);
    }

}
