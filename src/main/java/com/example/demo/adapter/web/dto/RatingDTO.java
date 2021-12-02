package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import org.springframework.hateoas.RepresentationModel;

public class RatingDTO extends RepresentationModel<RatingDTO>{

	private final String rating;
	
	private final UserRaterDto rater;
	
	private final PoliticianDto politician;
	
	private final String id;

	public String getRating() {
		return rating;
	}

	public UserRaterDto getRater() {
		return rater;
	}

	public PoliticianDto getPolitician() {
		return politician;
	}

	public String getId() {
		return id;
	}

	public RatingDTO(String rating, UserRaterDto rater, PoliticianDto politician, String id) {
		super();
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
		this.id = id;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "RatingDTO [rating=" + rating + ", rater=" + rater + ", politician=" + politician + ", id=" + id + "]";
	}

	public static RatingDTO from(PoliticiansRating rating) {
		return new RatingDTO(rating.score(), UserRaterDto.from(rating.whoRated()), new PoliticiansDtoMapper().mapToDTO(rating.whoWasRated()), rating.id().toString());
	}

}
