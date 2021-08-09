package com.example.demo.model.averageCalculator;

import com.example.demo.model.enums.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LowSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactonRate = Rating.LOW;
	
	public Rating getSatisfactonRate() {
		return satisfactonRate;
	}

	public LowSatisfactionAverageCalculator(double totalRating, double count) {
		super(totalRating, count);
	}

	@Override
	public double calculateAverage() {
		return calculateUtil();
	}
	
	private double calculateUtil() {
		double averageRating = BigDecimal.valueOf(getTotalRating() / (getCount() + 1D))
				.setScale(3, RoundingMode.CEILING)
				.doubleValue();

		return averageRating;
	}

}
