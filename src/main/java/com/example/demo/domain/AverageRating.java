package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;

public class AverageRating extends Score{

    private AverageRating(double rating) {
        super(rating);
    }

    public static AverageRating of(BigDecimal averageRating) {
        try {
            return new AverageRating(averageRating.doubleValue());
        } catch (ScoreHasExceededMaximumValueException e) {
            throw new ScoreHasExceededMaximumValueException(new AverageRatingMaximumValueException());
        }
    }

    public static AverageRating of(BigDecimal totalRatingAccumulated, int count, AverageRating previousAverageRating) {
        if (isTotalRatingAccumulatedZero(totalRatingAccumulated)) throw new IllegalStateException("""
                total rating accumulated must be greater than 0
                """);
        if (isCountZero(count)) throw new IllegalStateException("count must be greater than 0");

        double averageRatingCalculated = Rating.mapToSatisfactionRate(previousAverageRating.rating())
                .calculate(totalRatingAccumulated, count);

        return of(BigDecimal.valueOf(averageRatingCalculated));
    }

    private static boolean isCountZero(int count) {
        return count == 0;
    }

    private static boolean isTotalRatingAccumulatedZero(BigDecimal totalRatingAccumulated) {
        return totalRatingAccumulated.compareTo(BigDecimal.ZERO) == 0;
    }

}
