package com.example.demo.model;

import java.util.List;

public interface PoliticianMethods {

	double calculateAverageRating();
	
	double calculateTotalAmountOfRating(Double rating);
	
	long returnCountsOfRatings();
	
	boolean isEmptyCountOfRatings();
	
	List<PoliticiansRating> setListOfRaters(PoliticiansRating rater);
	
	String calculateFullName();
}
