package com.example.demo.domain.enums;

import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;

import java.math.BigDecimal;

public enum Rating {

	LOW {
		public BigDecimal calculate(BigDecimal totalRating, int count) {
			return new LowSatisfactionAverageCalculator(totalRating, count).calculateAverage();
		}
	},

	DECENT {
		public BigDecimal calculate(BigDecimal totalRating, int count) {
			return new DecentSatisfactionAverageCalculator(totalRating, count).calculateAverage();
		}
	},

	HIGH {
		public BigDecimal calculate(BigDecimal totalRating, int count) {
			return new HighSatisfactionAverageCalculator(totalRating, count).calculateAverage();
		}
	};

	public abstract BigDecimal calculate(BigDecimal totalRating, int count);

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
