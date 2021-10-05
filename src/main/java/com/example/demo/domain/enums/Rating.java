package com.example.demo.domain.enums;

import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;

import java.math.BigDecimal;

public enum Rating {

	LOW {
		public double calculate(BigDecimal totalRating, int count) {
			return new LowSatisfactionAverageCalculator(totalRating.doubleValue(), count).calculateAverage();
		}
	},

	DECENT {
		public double calculate(BigDecimal totalRating, int count) {
			return new DecentSatisfactionAverageCalculator(totalRating.doubleValue(), count).calculateAverage();
		}
	},

	HIGH {
		public double calculate(BigDecimal totalRating, int count) {
			return new HighSatisfactionAverageCalculator(totalRating.doubleValue(), count).calculateAverage();
		}
	};

	public abstract double calculate(BigDecimal totalRating, int count);

	public static Rating mapToSatisfactionRate(Double rating) {
		if (rating < 5D || rating == null) {
			Rating satisfaction = Rating.LOW;
			return satisfaction;
		} else if (rating < 8.89D) {
			Rating satisfaction = Rating.DECENT;
			return satisfaction;
		} else if (rating >= 8.89D) {
			Rating satisfaction = Rating.HIGH;
			return satisfaction;
		}

		return null;
	}
	
}
