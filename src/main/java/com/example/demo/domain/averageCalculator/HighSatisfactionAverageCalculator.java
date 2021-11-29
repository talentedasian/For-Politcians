package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.RoundingMode;

public class HighSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.HIGH;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public HighSatisfactionAverageCalculator(String averageRating, int count) {
		super(averageRating, count);
	}

	@Override
	public String calculateAverage(String summand) {
		return wilfred(2, RoundingMode.FLOOR, summand);
	}

}
