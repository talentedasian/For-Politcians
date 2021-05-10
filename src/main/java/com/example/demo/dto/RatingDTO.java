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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((politician == null) ? 0 : politician.hashCode());
		result = prime * result + ((rater == null) ? 0 : rater.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RatingDTO other = (RatingDTO) obj;
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
