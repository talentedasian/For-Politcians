package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HighSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.HIGH;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public HighSatisfactionAverageCalculator(double totalRating, double count) {
		super(totalRating, count);
	}

	@Override
	public double calculateAverage() {
		return calculateUtil();
	}
	
	private double calculateUtil() {
		double averageRating = BigDecimal.valueOf(getTotalRating() / (getCount()))
				.setScale(2, RoundingMode.DOWN)
				.doubleValue();
		
		return averageRating;
	}

}
