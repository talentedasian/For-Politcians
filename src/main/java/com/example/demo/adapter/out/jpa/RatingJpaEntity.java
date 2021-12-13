package com.example.demo.adapter.out.jpa;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class RatingJpaEntity {

    @Column(nullable = false, precision = 4, scale = 3)
    protected BigDecimal averageRating;

    public static RatingJpaEntity from(final AverageRating averageRating) {
        BigDecimal rating = AverageRating.hasRating(averageRating) ? averageRating.rating() : BigDecimal.ZERO;
        return new RatingJpaEntity(rating);
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    RatingJpaEntity() {
        super();
    }

    RatingJpaEntity(BigDecimal averageRating) {
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
