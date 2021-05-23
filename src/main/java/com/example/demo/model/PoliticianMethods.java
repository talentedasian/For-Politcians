package com.example.demo.model;

import java.util.List;

import com.example.demo.model.entities.PoliticiansRating;

public interface PoliticianMethods {

	double calculateAverageRating();
	
	double calculateTotalAmountOfRating(Double rating);
	
	long returnCountsOfRatings();
	
	boolean isEmptyCountOfRatings();
	
	String calculateFullName();

	List<PoliticiansRating> calculateListOfRaters(PoliticiansRating rater);
}
