package com.example.demo.model;

public interface PoliticianMethods {

	void calculateAverageRating();
	
	void calculateTotalAmountOfRating(Double rating);
	
	long returnCountsOfRatings();
	
	boolean isEmptyCountOfRatings();
	
	void setListOfRaters(PoliticiansRating rater);
	
	String calculateFullName();
}
