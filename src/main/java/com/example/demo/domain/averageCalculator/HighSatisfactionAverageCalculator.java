package com.example.demo.domain.averageCalculator;

import com.example.demo.domain.enums.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HighSatisfactionAverageCalculator extends AverageCalculator{

	private final Rating satisfactionRating = Rating.HIGH;
	
	public Rating getSatisfactionRating() {
		return satisfactionRating;
	}

	public HighSatisfactionAverageCalculator(BigDecimal totalRating, int count) {
		super(totalRating, count);
	}

	@Override
	public BigDecimal calculateAverage() {
		return calculateUtil();
	}
	
	private BigDecimal calculateUtil() {
		BigDecimal averageRating = getTotalRating().divide(getCount(), 2, RoundingMode.FLOOR);

		return averageRating;
	}

}
