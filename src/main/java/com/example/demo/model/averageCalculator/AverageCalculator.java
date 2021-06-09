package com.example.demo.model.averageCalculator;

public abstract class AverageCalculator {
	
	private final double totalRating;
	private final double count;

	public double getTotalRating() {
		return totalRating;
	}

	public double getCount() {
		return count;
	}

	public AverageCalculator(double totalRating, double count) {
		super();
		this.totalRating = totalRating;
		this.count = count;
	}
	
	public abstract double calculateAverage();
}
