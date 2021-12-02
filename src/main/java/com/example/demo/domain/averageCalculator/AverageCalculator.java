package com.example.demo.domain.averageCalculator;

import java.math.BigDecimal;

public abstract class AverageCalculator implements Calculator{
	
	private final BigDecimal totalRating;
	private final BigDecimal count;

	protected BigDecimal getTotalRating() {
		return totalRating;
	}

	protected BigDecimal getCount() {
		return count;
	}

	public AverageCalculator(BigDecimal totalRating, int count) {
		org.springframework.util.Assert.state(totalRating.compareTo(BigDecimal.ZERO) > 0,
				"total RatingJpaEntity must not be negative");
		
		if (!isNumberPositive(count)) {
			count = 0;
		}
		
		this.totalRating = totalRating;
		this.count = new BigDecimal(count);
	}
	
	private boolean isNumberPositive(double number) {
		return !String.valueOf(number).contains("-");
	}
	
}
