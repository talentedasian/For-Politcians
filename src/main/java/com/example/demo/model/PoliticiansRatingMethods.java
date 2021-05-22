package com.example.demo.model;

import com.example.demo.model.enums.PoliticalParty;

public interface PoliticiansRatingMethods {

	double calculateRating(double rating);
	
	Politicians calculatePolitician(Politicians politician);
	
	UserRater calculateRater(String subject, String id, PoliticalParty politicalParty); 
	
}
