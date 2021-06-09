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
	
	@Override
	public String toString() {
		return "Rating [totalRating=" + totalRating + ", averageRating=" + averageRating + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averageRating == null) ? 0 : averageRating.hashCode());
		result = prime * result + ((totalRating == null) ? 0 : totalRating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (averageRating == null) {
			if (other.averageRating != null)
				return false;
		} else if (!averageRating.equals(other.averageRating))
			return false;
		if (totalRating == null) {
			if (other.totalRating != null)
				return false;
		} else if (!totalRating.equals(other.totalRating))
			return false;
		return true;
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
