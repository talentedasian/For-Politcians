package com.example.demo.domain;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;

public class AverageRating extends Score{

    private AverageRating(double rating) {
        super(rating);
    }

    public static AverageRating of(BigDecimal averageRating) {
        return new AverageRating(averageRating.doubleValue());
    }

    public static AverageRating of(BigDecimal totalRatingAccumulated, int count, AverageRating previousAverageRating) {
        if (isTotalRatingAccumulatedZero(totalRatingAccumulated)) throw new IllegalStateException("""
                total rating accumulated must be greater than 0
                """);
        if (isCountZero(count)) throw new IllegalStateException("count must be greater than 0");

        double averageRatingCalculated = Rating.mapToSatisfactionRate(previousAverageRating.rating())
                .calculate(totalRatingAccumulated, count);

        if (averageRatingGreaterThanTen(averageRatingCalculated))
            throw new ScoreHasExceededMaximumValueException("""
                    Average rating to be calculated will be larger than 10. Average ratings must only be
                    greater than 0 and less than 10. This often happens when a rating has exceeded the max
                    Score applied which is 10. 
                    """);

        return new AverageRating(averageRatingCalculated);
    }

    private static boolean averageRatingGreaterThanTen(double averageRating) {
        BigDecimal LIMIT = BigDecimal.TEN;
        return BigDecimal.valueOf(averageRating).compareTo(LIMIT) >= 1;
    }

    private static boolean isCountZero(int count) {
        return count == 0;
    }

    private static boolean isTotalRatingAccumulatedZero(BigDecimal totalRatingAccumulated) {
        return totalRatingAccumulated.compareTo(BigDecimal.ZERO) == 0;
    }

}
