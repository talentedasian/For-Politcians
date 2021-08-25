package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.entities.Rating;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RatingJpaEntity {

    @Column(nullable = false, precision = 3, scale = 3)
    protected Double totalRating;

    @Column(nullable = false, precision = 3, scale = 4)
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

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public RatingJpaEntity() {
        super();
    }

    public RatingJpaEntity(Double totalRating, Double averageRating) {
        super();
        this.totalRating = totalRating;
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "RatingJpaEntity{" +
                "totalRating=" + totalRating +
                ", averageRating=" + averageRating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingJpaEntity that = (RatingJpaEntity) o;

        if (!Objects.equals(totalRating, that.totalRating)) return false;
        if (!Objects.equals(averageRating, that.averageRating)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = totalRating != null ? totalRating.hashCode() : 0;
        result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
        return result;
    }

    public Rating toRating() {
        return new Rating(totalRating, averageRating);
    }
}
