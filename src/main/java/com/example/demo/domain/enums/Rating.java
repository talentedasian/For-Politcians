package com.example.demo.domain.enums;

import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;

public enum Rating {

	LOW {
		public String calculate(String rating, int count, String newSummand) {
			return new LowSatisfactionAverageCalculator(rating.toString(), count).calculateAverage(newSummand);
		}
	},

	DECENT {
		public String calculate(String rating, int count, String newSummand) {
			return new DecentSatisfactionAverageCalculator(rating.toString(), count).calculateAverage(newSummand);
		}
	},

	HIGH {
		public String calculate(String rating, int count, String newSummand) {
			return new HighSatisfactionAverageCalculator(rating, count).calculateAverage(newSummand);
		}
	};

	public abstract String calculate(String totalRating, int count, String newSummand);

	public static Rating mapToSatisfactionRate(Double rating) {
		if (rating < 5D || rating == null) {
			return LOW;
		} else if (rating < 8.89D) {
			return DECENT;
		} else if (rating >= 8.89D) {
			return HIGH;
		}

		return LOW;
	}
	
}
