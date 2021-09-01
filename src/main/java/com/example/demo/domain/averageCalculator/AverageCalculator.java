package com.example.demo.domain.averageCalculator;

public abstract class AverageCalculator implements Calculator{
	
	private final double totalRating;
	private final double count;

	public double getTotalRating() {
		return totalRating;
	}

	public double getCount() {
		return count;
	}

	public AverageCalculator(double totalRating, double count) {
		org.springframework.util.Assert.state(isNumberPositive(totalRating),
				"total RatingJpaEntity must not be negative");
		
		if (!isNumberPositive(count)) {
			count = 0;
		}
		
		this.totalRating = totalRating;
		this.count = count;
	}
	
	private boolean isNumberPositive(double number) {
		return !String.valueOf(number).contains("-");
	}
	
}
