package com.example.demo.dto;

import com.example.demo.model.UserRater;

public class RatingDTO {

	private Double rating;
	
	private UserRater rater;
	
	private PoliticianDTO politician;

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public UserRater getRater() {
		return rater;
	}

	public void setRater(UserRater rater) {
		this.rater = rater;
	}

	public PoliticianDTO getPolitician() {
		return politician;
	}

	public void setPolitician(PoliticianDTO politician) {
		this.politician = politician;
	}

	public RatingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingDTO(Double rating, UserRater rater, PoliticianDTO politician) {
		super();
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
	}

	@Override
	public String toString() {
		return "RatingDTO [rating=" + rating + ", rater=" + rater + ", politician=" + politician + "]";
	}
	
}
