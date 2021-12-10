package com.example.demo.adapter.out.jpa;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.TotalRatingAccumulated;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class RatingJpaEntity {

    @Column(nullable = false)
    protected BigDecimal totalRating;

    @Column(nullable = false, precision = 4, scale = 3)
    protected BigDecimal averageRating;

    public static RatingJpaEntity from(TotalRatingAccumulated totalRatingAccumulated, final AverageRating averageRating) {
        String rating = AverageRating.hasRating(averageRating) ? averageRating.rating().toString() : "0";
        return new RatingJpaEntity(totalRatingAccumulated.totalRating().toString(), rating);
    }

    public String getTotalRating() {
        return totalRating.toString();
    }

    public String getAverageRating() {
        return averageRating.toString();
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = new BigDecimal(totalRating);
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = new BigDecimal(averageRating);
    }

    public RatingJpaEntity() {
        super();
    }

    public RatingJpaEntity(String totalRating, String averageRating) {
        super();
        this.totalRating = new BigDecimal(totalRating);
        this.averageRating = new BigDecimal(averageRating);
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
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
        return Objects.equals(averageRating, that.averageRating);
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public int hashCode() {
        int result = totalRating != null ? totalRating.hashCode() : 0;
        result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
        return result;
    }

}
