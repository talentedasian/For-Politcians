package com.example.demo.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;

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
	private transient AverageCalculator calculator;
	
	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rating(Double totalRating, Double averageRating, AverageCalculator calculator) {
		super();
		this.totalRating = totalRating;
		this.averageRating = averageRating;
		this.calculator = calculator;
	}
	
	public double calculateAverage() {
		double rating = calculator.calculateAverage();
		this.averageRating = rating;
		
		return rating;
	}
	
	public double calculateTotalAmountOfRating(Double ratingToAdd, Double countOfRatings) {
		if (totalRating == null) {
			double rating = BigDecimal.valueOf(ratingToAdd)
			.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
			this.totalRating = rating;
			calculator = returnAverageCalculator(countOfRatings);
			
			return rating;
		}
		
		double rating = BigDecimal.valueOf(totalRating + ratingToAdd)
				.setScale(3, RoundingMode.UP).doubleValue();
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
	
}
