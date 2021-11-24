package com.example.demo.domain.enums;

public enum Rating {

	LOW,

	DECENT,

	HIGH;

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
