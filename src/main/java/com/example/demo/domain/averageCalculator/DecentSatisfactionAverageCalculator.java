package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecentSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.DECENT;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public DecentSatisfactionAverageCalculator(double totalRating, double count) {
		super(totalRating, count);
	}

	@Override
	public double calculateAverage() {
		return calculateUtil();
	}
	
	private double calculateUtil() {
		double averageRating = BigDecimal.valueOf(getTotalRating() / (getCount()))
				.setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
		
		return averageRating;
	}

}
