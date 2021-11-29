package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.RoundingMode;

public class DecentSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.DECENT;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public DecentSatisfactionAverageCalculator(String averageRating, int count) {
		super(averageRating, count);
	}

	@Override
	public String calculateAverage(String summand) {
		return wilfred(2, RoundingMode.HALF_UP, summand);
	}

}
