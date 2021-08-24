package com.example.demo.domain.entities;

import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;

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

	private  AverageCalculator calculator= null;
	
	public Rating() {
		super();
	}

	public Rating(Double totalRating, Double averageRating) {
		super();
		this.totalRating = totalRating;
		this.averageRating = averageRating;
	}
	
	public double calculateAverage(double ratingToAdd, double countsOfRatings){
		calculateTotalAmountOfRating(ratingToAdd, countsOfRatings);
		double rating = calculator.calculateAverage();
		this.averageRating = rating;
		
		return rating;
	}
	
	public void calculateTotalAmountOfRating(Double ratingToAdd, Double countOfRatings) {
		if (totalRating == null || totalRating == 0D) {
			double rating = BigDecimal.valueOf(ratingToAdd)
			.setScale(4, RoundingMode.HALF_DOWN).doubleValue();
			this.totalRating = rating;
			calculator = returnAverageCalculator(countOfRatings);
			return;
		}
		
		double rating = BigDecimal.valueOf(totalRating + ratingToAdd)
				.setScale(4, RoundingMode.UP).doubleValue();
		this.totalRating = rating;
		calculator = returnAverageCalculator(countOfRatings);
		return;
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
