package com.example.demo.domain.averageCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AverageCalculator implements Calculator{
	
	private final String averageRating;
	private final int count;

	public String getAverageRating() {
		return averageRating;
	}

	public int getCount() {
		return count;
	}

	public AverageCalculator(String averageRating, int count) {
		org.springframework.util.Assert.state(isNumberPositive(averageRating),
				"total RatingJpaEntity must not be negative");
		
		if (!isNumberPositive(String.valueOf(count))) {
			count = 0;
		}
		
		this.averageRating = averageRating;
		this.count = count;
	}
	
	private boolean isNumberPositive(String number) {
		return !number.contains("-");
	}

	protected String wilfred(int scale, RoundingMode roundingMode, String newSummand) {
		BigDecimal mean = new BigDecimal(averageRating);
		return mean.add(new BigDecimal(newSummand).subtract(mean).divide(new BigDecimal(getCount() + 1), scale, roundingMode))
				.setScale(scale, roundingMode).toString();
	}


}
