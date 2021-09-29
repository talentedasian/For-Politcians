package com.example.demo.domain.entities;

import com.example.demo.domain.Score;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;

public class PoliticiansRating {

	private Integer id;

	private Score rating;

	private UserRater rater;

	private Politicians politician;
	
	public Integer id() {
		return id;
	}

	public double score() {
		return rating.rating();
	}

	public UserRater whoRated() {
		return rater;
	}

	public Politicians whoWasRated() {
		return politician;
	}

	PoliticiansRating(Integer id, Score score, UserRater rater, Politicians politician) {
		this.id = id;
		this.rating = score;
		this.rater = rater;
		this.politician = politician;
	}

	@Override
	public String toString() {
		return "PoliticiansRating [id=" + id + ", rating=" + rating.rating() + ", rater=" + rater +
				", politician=" + politician + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PoliticiansRating other = (PoliticiansRating) obj;
		if (other.id == null) return false;
		if (other.rater == null) return false;
		return other.id.equals(id) && other.rater.equals(rater);
	}

	public void ratePolitician(UserRateLimitService rateLimitService) throws UserRateLimitedOnPoliticianException {
		if (rater.canRate(rateLimitService, politician.politicianNumber())) {
			politician.rate(this);
			rater.rateLimitUser(rateLimitService, politician.politicianNumber());
			return;
		}

		long daysLeft = rater.daysLeftToRate(rateLimitService, PoliticianNumber.of(politician.retrievePoliticianNumber()));
		throw new UserRateLimitedOnPoliticianException(daysLeft, politician.politicianNumber());
	}

	public void deleteRating() {
		politician.deleteRate(this);
	}

	public static class Builder {
		private String id;

		private Score rating;

		private UserRater rater;

		private Politicians politician;

		public Builder () {}

		public Builder (Politicians politicians) {
			this.politician = politicians;
		}

		public Builder (Politicians.PoliticiansBuilder politiciansBuilder) {
			this.politician = politiciansBuilder.build();
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setRating(Score rating) {
			this.rating = rating;
			return this;
		}

		public Builder setRater(UserRater rater) {
			this.rater = rater;
			return this;
		}

		public Builder setPolitician(Politicians politician) {
			this.politician = politician;
			return this;
		}

		public PoliticiansRating build() {
			try {
				return new PoliticiansRating(Integer.valueOf(id), rating, rater, politician);
			} catch (NumberFormatException e) {
				return new PoliticiansRating(null, rating, rater, politician);
			}
		}

	}

}
