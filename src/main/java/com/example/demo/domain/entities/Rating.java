package com.example.demo.domain.entities;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.averageCalculator.Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Rating {

	protected Double totalRating;

	protected Double averageRating;
	
	public Double getTotalRating() {
		return totalRating;
	}

	public Double getAverageRating() {
		return averageRating;
	}
	
	public Rating() {
		super();
	}

	public Rating(Double totalRating, Double averageRating) {
		this.totalRating = totalRating;
		this.averageRating = averageRating;
	}

	public Rating(Double totalRating, AverageRating averageRating) {
		this.totalRating = totalRating;
		this.averageRating = averageRating.rating();
	}
	
	public double calculateAverage(double ratingToAdd, Calculator calculator){
		calculateTotalRating(ratingToAdd);
		double rating = calculator.calculateAverage();
		this.averageRating = rating;
		
		return rating;
	}

	private void calculateTotalRating(Double ratingToAdd) {
		this.totalRating = calculateTotalAmountOfRating(ratingToAdd);
	}

	public double calculateTotalAmountOfRating(Double ratingToAdd) {
		if (totalRating == null || totalRating == 0D) {
			return BigDecimal.valueOf(ratingToAdd)
			.setScale(4, RoundingMode.HALF_DOWN).doubleValue();
		}
		
		return BigDecimal.valueOf(totalRating + ratingToAdd)
				.setScale(4, RoundingMode.UP).doubleValue();
	}
	
	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "Rating [totalRating=" + totalRating + ", averageRating=" + averageRating + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Rating rating = (Rating) o;

		if (!Objects.equals(totalRating, rating.totalRating)) return false;
		return Objects.equals(averageRating, rating.averageRating);
	}

	@Override
	public int hashCode() {
		int result = totalRating != null ? totalRating.hashCode() : 0;
		result = 31 * result + (averageRating != null ? averageRating.hashCode() : 0);
		return result;
	}
}
