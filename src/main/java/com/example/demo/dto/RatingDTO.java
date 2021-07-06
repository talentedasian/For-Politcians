package com.example.demo.dto;

import org.springframework.hateoas.RepresentationModel;

import com.example.demo.model.entities.UserRater;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((politician == null) ? 0 : politician.hashCode());
		result = prime * result + ((rater == null) ? 0 : rater.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RatingDTO other = (RatingDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (politician == null) {
			if (other.politician != null)
				return false;
		} else if (!politician.equals(other.politician))
			return false;
		if (rater == null) {
			if (other.rater != null)
				return false;
		} else if (!rater.equals(other.rater))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}
	
}
