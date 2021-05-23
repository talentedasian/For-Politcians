package com.example.demo.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.demo.model.averageCalculator.AverageCalculator;

@Embeddable
public class Rating {
	
	@Column(nullable = false, precision = 3, scale = 2)
	protected Double totalRating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	protected Double averageRating;
	
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

	
	public double calculateAverage(Double countOfRatings) {
		double rating = calculator.calculateAverage();
		this.averageRating = rating;
		
		return rating;
	}
	
	public double calculateTotalAmountOfRating(Double ratingToAdd, Double countOfRatings) {
		if (totalRating == null) {
			double rating = BigDecimal.valueOf(ratingToAdd)
			.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
			this.totalRating = rating;
			
			return rating;
		}
		
		double rating = BigDecimal.valueOf(totalRating + ratingToAdd)
				.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
		this.totalRating = rating;
		
		return rating;
	}
	
}
