package com.example.demo.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;

@Embeddable
public class Rating {
	
	@Column(nullable = false, precision = 3, scale = 2)
	protected final Double totalRating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	protected final Double averageRating;
	
	public Double getTotalRating() {
		return totalRating;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	private transient AverageCalculator calculator;
	
	public Rating(Double totalRating, Double averageRating, AverageCalculator calculator) {
		super();
		this.totalRating = totalRating;
		this.averageRating = averageRating;
		this.calculator = calculator;
	}
	
	@Override
	public String toString() {
		return "Rating [totalRating=" + totalRating + ", averageRating=" + averageRating + "]";
	}

	/*
	 * Fields are immutable. This method returns a newly created Rating object with the new 
	 * averageRating field and old fields.
	 */
	public Rating calculateAverage(Double countOfRatings) {
		double rating = calculator.calculateAverage();
		
		return new Rating(totalRating, rating, returnAverageCalculator(countOfRatings));
	}
	
	public Rating calculateTotalAmountOfRating(Double ratingToAdd, Double countOfRatings) {
		if (totalRating == null) {
			double rating = BigDecimal.valueOf(ratingToAdd)
			.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
			
			return new Rating(rating, averageRating, returnAverageCalculator(countOfRatings));
		}
		
		double rating = BigDecimal.valueOf(totalRating + ratingToAdd)
				.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
		
		return new Rating(rating, averageRating, returnAverageCalculator(countOfRatings));
	}
	
	private AverageCalculator returnAverageCalculator(Double count) {
		if (this.averageRating < 5D) {
			return new LowSatisfactionAverageCalculator(totalRating, count);
		} else if (this.averageRating < 8.89D) {
			return new DecentSatisfactionAverageCalculator(totalRating, count);
		} else if (this.averageRating >= 8.89D) {
			return new HighSatisfactionAverageCalculator(totalRating, count);
		}
		
		return null;
	}
	
}
