package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.RoundingMode;

public class LowSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactonRate = Rating.LOW;
	
	public Rating getSatisfactonRate() {
		return satisfactonRate;
	}

	public LowSatisfactionAverageCalculator(String averageRating, int count) {
		super(averageRating, count);
	}

	@Override
	public String calculateAverage(String summand) {
		return wilfred(3, RoundingMode.CEILING, summand);
	}

}
