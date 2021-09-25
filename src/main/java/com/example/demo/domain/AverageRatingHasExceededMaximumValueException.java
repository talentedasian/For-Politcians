package com.example.demo.domain;

public class AverageRatingHasExceededMaximumValueException extends RuntimeException{

    public AverageRatingHasExceededMaximumValueException(double rating) {
        super(String.format(
                """
                Rating %s exceeded maximum average rating which is 10. This often 
                happens when Scores that are used to calculated all the accumulated 
                total rating/Score has exceeded the maximum value which is also 10.
                """, rating));
    }
}
