package com.example.demo.model.averageCalculator;

public abstract class AverageCalculator {
	
	private double totalRating;
	private double count;

	public double getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public abstract double calculateAverage();

	public AverageCalculator(double totalRating, double count) {
		super();
		this.totalRating = totalRating;
		this.count = count;
	}
	
}
