package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LowSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactonRate = Rating.LOW;
	
	public Rating getSatisfactonRate() {
		return satisfactonRate;
	}

	public LowSatisfactionAverageCalculator(BigDecimal totalRating, int count) {
		super(totalRating, count);
	}

	@Override
	public BigDecimal calculateAverage() {
		return calculateUtil();
	}
	
	private BigDecimal calculateUtil() {
		BigDecimal averageRating = getTotalRating().divide(getCount(), 3, RoundingMode.CEILING);

		return averageRating;
	}

}
