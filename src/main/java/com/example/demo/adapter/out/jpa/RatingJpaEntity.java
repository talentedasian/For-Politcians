package com.example.demo.adapter.out.jpa;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RatingJpaEntity {
    @Column(nullable = false, precision = 3, scale = 4)
    protected Double averageRating;

    public static RatingJpaEntity from(final AverageRating averageRating) {
        double rating = AverageRating.hasRating(averageRating) ? averageRating.averageRating() : 0;
        return new RatingJpaEntity(rating);
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public RatingJpaEntity() {
        super();
    }

    public RatingJpaEntity(Double averageRating) {
        super();
        this.averageRating = averageRating;
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "RatingJpaEntity{" +
                ", averageRating=" + averageRating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingJpaEntity that = (RatingJpaEntity) o;

        return Objects.equals(averageRating, that.averageRating);
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public int hashCode() {
        int result = averageRating != null ? averageRating.hashCode() : 0;
        return result;
    }

}
