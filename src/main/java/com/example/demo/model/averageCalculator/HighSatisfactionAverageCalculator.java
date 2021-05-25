package com.example.demo.model.averageCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.demo.model.enums.Rating;

public class HighSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.HIGH;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public HighSatisfactionAverageCalculator(double totalRating, double count) {
		super(totalRating, count);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateAverage() {
		// TODO Auto-generated method stub
		return calculateUtil();
	}
	
	private double calculateUtil() {
		double averageRating = BigDecimal.valueOf(getTotalRating() / (getCount() + 1D))
				.setScale(2, RoundingMode.DOWN)
				.doubleValue();
		
		return averageRating;
	}

}
