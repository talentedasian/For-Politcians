package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.domain.entities.Rating;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public class RatingJpaEntity {

    @Column(nullable = false, precision = 3, scale = 2)
    protected Double totalRating;

    @Column(nullable = false, precision = 3, scale = 2)
    protected Double averageRating;

    public static RatingJpaEntity from(Rating rating) {
        return new RatingJpaEntity(rating.getTotalRating(), rating.getAverageRating());
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public AverageCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(AverageCalculator calculator) {
        this.calculator = calculator;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Transient
    private transient AverageCalculator calculator= null;

    public RatingJpaEntity() {
        super();
    }

    public RatingJpaEntity(Double totalRating, Double averageRating) {
        super();
        this.totalRating = totalRating;
        this.averageRating = averageRating;
    }

    public double calculateAverage() {
        double rating = calculator.calculateAverage();
        this.averageRating = rating;

        return rating;
    }

    public double calculateTotalAmountOfRating(Double ratingToAdd, Double countOfRatings) {
        if (totalRating == null) {
            double rating = BigDecimal.valueOf(ratingToAdd)
                    .setScale(4, RoundingMode.HALF_DOWN).doubleValue();
            this.totalRating = rating;
            calculator = returnAverageCalculator(countOfRatings);

            return rating;
        }

        double rating = BigDecimal.valueOf(totalRating + ratingToAdd)
                .setScale(4, RoundingMode.UP).doubleValue();
        this.totalRating = rating;
        calculator = returnAverageCalculator(countOfRatings);

        return rating;
    }

    @Override
    public String toString() {
        return "Rating [totalRating=" + totalRating + ", averageRating=" + averageRating + "]";
    }

    public AverageCalculator returnAverageCalculator(Double count) {
        if (averageRating < 5D) {
            return new LowSatisfactionAverageCalculator(totalRating, count);
        } else if (averageRating < 8.89D) {
            return new DecentSatisfactionAverageCalculator(totalRating, count);
        } else if (averageRating >= 8.89D) {
            return new HighSatisfactionAverageCalculator(totalRating, count);
        }

        return null;
    }

    public Rating toRating() {
        return new Rating(totalRating, averageRating);
    }
}
