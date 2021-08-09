package com.example.demo.adapter.dto;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.domain.entities.UserRater;

public class RatingDTO extends RepresentationModel<RatingDTO>{

	private final Double rating;
	
	private final UserRater rater;
	
	private final PoliticianDTO politician;
	
	private final String id;

	public Double getRating() {
		return rating;
	}

	public UserRater getRater() {
		return rater;
	}

	public PoliticianDTO getPolitician() {
		return politician;
	}

	public String getId() {
		return id;
	}

	public RatingDTO(Double rating, UserRater rater, PoliticianDTO politician, String id) {
		super();
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
		this.id = id;
	}

	@Override
	public String toString() {
		return "RatingDTO [rating=" + rating + ", rater=" + rater + ", politician=" + politician + ", id=" + id + "]";
	}

}
